import json
import boto3
import os
import pg8000.native

def get_secret():
    secret_arn = os.environ['SECRET_ARN']
    client = boto3.client('secretsmanager')
    response = client.get_secret_value(SecretId=secret_arn)
    return json.loads(response['SecretString'])

def lambda_handler(event, context):
    try:
        # 1. Obtener credenciales
        secrets = get_secret()
        # Nota: Ajusta las keys según como guardaste el JSON en secrets manager
        # En tu terraform usaste "spring.datasource.password", vamos a parsear eso o
        # idealmente guardar claves simples como "username", "password", "host".
        # Asumiré claves estándar para este ejemplo de python puro:
        
        # Parseo simple basado en tu terraform anterior
        db_user = secrets.get('spring.datasource.username')
        db_password = secrets.get('spring.datasource.password')
        
        # Estas variables vienen del entorno de Lambda
        db_host = os.environ['DB_HOST']
        db_name = os.environ['DB_NAME']
        
        # 2. Conectar a la DB
        conn = pg8000.native.Connection(
            user=db_user,
            password=db_password,
            host=db_host,
            database=db_name
        )
        
        # 3. Crear tabla si no existe y escribir
        conn.run("CREATE TABLE IF NOT EXISTS logs (id SERIAL PRIMARY KEY, info TEXT)")
        conn.run("INSERT INTO logs (info) VALUES (:info)", info='Insert desde Lambda')
        
        conn.close()
        
        return {'statusCode': 200, 'body': json.dumps('Datos escritos en RDS correctamente')}
        
    except Exception as e:
        print(f"Error: {str(e)}")
        return {'statusCode': 500, 'body': json.dumps(f'Error DB: {str(e)}')}