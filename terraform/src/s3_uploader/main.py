import json
import boto3
import os
import datetime

def lambda_handler(event, context):
    s3 = boto3.client('s3')
    bucket_name = os.environ['BUCKET_NAME']
    
    # Creamos un nombre de archivo único
    timestamp = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S")
    file_name = f"log-{timestamp}.txt"
    file_content = f"Este es un archivo de prueba generado a las {timestamp}"
    
    try:
        s3.put_object(Bucket=bucket_name, Key=file_name, Body=file_content)
        return {
            'statusCode': 200,
            'body': json.dumps(f'Exito: Archivo {file_name} subido a {bucket_name}')
        }
    except Exception as e:
        print(f"Error: {str(e)}")
        return {
            'statusCode': 500,
            'body': json.dumps(f'Error subiendo archivo: {str(e)}')
        }