import hashlib
import os

import pyodbc
import struct
from azure import identity
from dotenv import load_dotenv
from flask import Flask, jsonify, request
from flask_restful import reqparse, Api, Resource
from opencensus.ext.azure.trace_exporter import AzureExporter
from opencensus.ext.flask.flask_middleware import FlaskMiddleware
from opencensus.trace.samplers import ProbabilitySampler
from werkzeug.security import generate_password_hash, check_password_hash

# Initialize Flask
app = Flask(__name__)

load_dotenv()

flask_env = os.getenv("FLASK_ENV")
connection_string = os.getenv('AZURE_SQL_CONNECTIONSTRING')

# Setup Azure Monitor
if 'APPINSIGHTS_KEY' in os.environ:
    middleware = FlaskMiddleware(
        app,
        exporter=AzureExporter(connection_string="InstrumentationKey={0}".format(os.environ['APPINSIGHTS_KEY'])),
        sampler=ProbabilitySampler(rate=1.0),
    )

# Setup Flask Restful framework
api = Api(app)
parser = reqparse.RequestParser()

# Parser for Customer POST
customer_post_parser = reqparse.RequestParser()
customer_post_parser.add_argument('customerid', type=str, required=True, help="Customer id is required")
customer_post_parser.add_argument('customername', type=str, required=True, help="Customer username is required")
customer_post_parser.add_argument('customerpassword', type=str, required=True, help="Customer password is required")

# Parser for CustomerTransaction POST
transaction_post_parser = reqparse.RequestParser()
transaction_post_parser.add_argument('transactionid', type=str, required=True, help="Transaction id is required")
transaction_post_parser.add_argument('customerid', type=str, required=True, help="Customer id is required")
transaction_post_parser.add_argument('date', type=str, required=True, help="Date is required")
transaction_post_parser.add_argument('amount', type=float, required=True, help="Amount is required")
transaction_post_parser.add_argument('type', type=str, required=True, help="Transaction type is required")
transaction_post_parser.add_argument('description', type=str, required=False)

def get_conn():
    credential = identity.DefaultAzureCredential(exclude_interactive_browser_credential=False)
    token_bytes = credential.get_token("https://database.windows.net/.default").token.encode("UTF-16-LE")
    token_struct = struct.pack(f'<I{len(token_bytes)}s', len(token_bytes), token_bytes)
    SQL_COPT_SS_ACCESS_TOKEN = 1256  # This connection option is defined by microsoft in msodbcsql.h
    conn = pyodbc.connect(connection_string, attrs_before={SQL_COPT_SS_ACCESS_TOKEN: token_struct})
    return conn

# Customer Class
class Customer(Resource):
    def get(self, customer_username, customer_password):
        with get_conn() as conn:
            cursor = conn.cursor()

            hashed_password = generate_password_hash(customer_password)

            sql_statement = "SELECT * FROM Customer WHERE CustomerName = ? AND CustomerPassword = ?"
            cursor.execute(sql_statement, (customer_username, hashed_password))

            row = cursor.fetchone()
            return row

    def post(self):
        with get_conn() as conn:
            args = parser.parse_args()
            cursor = conn.cursor()

            hashed_password = generate_password_hash(args["customerpassword"])

            sql_statement = "INSERT INTO Customer (CustomerID, CustomerUsername, CustomerPassword) VALUES (?, ?, ?)"

            # Create a dictionary with matching keys
            parameters = {
                "CustomerID": args["customerid"],
                "CustomerUsername": args["customername"],
                "CustomerPassword": hashed_password,
            }

            cursor.execute(sql_statement, parameters)

# Customers Class
class Customers(Resource):
    def get(self):
        with get_conn() as conn:
            cursor = conn.cursor()

            sql_statement = "SELECT * FROM Customer"
            cursor.execute(sql_statement)

            rows = cursor.fetchall()
            columns = [column[0] for column in cursor.description]
            customers_list = [dict(zip(columns, row)) for row in rows]
            return jsonify(customers_list)

# CustomerTransaction Class
class CustomerTransaction(Resource):
    def get(self, customer_id):
        with get_conn() as conn:
            cursor = conn.cursor()
            sql_statement = "SELECT * FROM CustomerTransaction WHERE CustomerID = ?"
            cursor.execute(sql_statement, customer_id)

            rows = cursor.fetchall()
            return rows

    def post(self):
        with get_conn() as conn:
            args = parser.parse_args()
            cursor = conn.cursor()

            transaction_sql_statement = "INSERT INTO CustomerTransaction (CustomerName, CustomerPassword, ) VALUES (?, ?, ?)"

            # Create a dictionary with matching keys
            parameters = {
                "TransactionID": args["transactionid"],
                "CustomerID": args["customerid"],
                "Date": args["date"],
                "Amount": args["amount"],
                "Type": args["type"],
                "Description": args["description"],
            }

            cursor.execute(transaction_sql_statement, parameters)

# Create API routes
api.add_resource(Customer, '/customer', '/customer/<string:customer_username>')
api.add_resource(Customers, '/customers')
api.add_resource(CustomerTransaction, '/customer_transaction/<string:customer_id>', '/customer_transaction')

if __name__ == "__main__":
  app.run(host="0.0.0.0")