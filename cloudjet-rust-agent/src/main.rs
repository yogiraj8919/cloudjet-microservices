use futures_util::stream::StreamExt;
use lapin::{
    options::*,
    types::FieldTable,
    Connection,
    ConnectionProperties,
};
use serde::{Deserialize, Serialize};



#[derive(Debug,Deserialize,Serialize)]
struct ProvisionRequest{
    #[serde(rename = "dbId")]
    db_id:i64
}


#[tokio::main]
async fn main() {
    println!("CloudJet Rust agent started");

    let addr = "amqp://admin:admin@127.0.0.1:5672/%2f";

    let conn = Connection::connect(addr, ConnectionProperties::default())
        .await
        .expect("Failed to connect");

    let channel = conn.create_channel()
                .await
                .expect("Failed to create channel");

    println!("Connected to RabbitMQ");

    let mut consumer = channel
                    .basic_consume("rust.test.queue", "rust_agent", BasicConsumeOptions::default(), FieldTable::default())
                    .await
                    .expect("Failed to create consumer");


    println!("Waiting for messages...");

    while let Some(Ok(delivery)) = consumer.next().await {
        let request: ProvisionRequest = serde_json::from_slice(&delivery.data)
            .expect("Failed to parse JSON");

        println!("Received DB ID: {}", request.db_id);

        println!("Full request: {:?}", request);

        delivery
            .ack(BasicAckOptions::default())
            .await
            .unwrap();
    }






}
