import json
import sys
from azure.servicebus import ServiceBusClient, ServiceBusMessage

# Replace with your actual connection string and topic
connection_str = "Endpoint=sb://X.servicebus.windows.net/;SharedAccessKeyName=SendAndListenPolicy;SharedAccessKey=Y"
topic_name = "shutdown-test"

def send_messages_from_json_array(file_path):
  with open(file_path, "r") as f:
    messages = json.load(f)  # This loads the full list of messages

  servicebus_client = ServiceBusClient.from_connection_string(conn_str=connection_str)
  sender = servicebus_client.get_topic_sender(topic_name=topic_name)

  try:
    with sender:
      for entry in messages:

        # Serialize body to JSON string
        payload = json.dumps(entry)

        # Create and send message
        sb_message = ServiceBusMessage(payload)
        sender.send_messages(sb_message)

        print(f"✅ Sent message {payload}")
  finally:
    servicebus_client.close()

if __name__ == "__main__":
  if len(sys.argv) != 2:
    print("Usage: python3 send_asb_messages_from_file.py <json_file_path>")
    sys.exit(1)

  file_path = sys.argv[1]
  send_messages_from_json_array(file_path)
