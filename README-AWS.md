aws s3 mb s3://test --profile localstack
aws s3 ls --profile localstack

awslocal kinesis list-streams

aws lambda invoke --profile=localstack \
--cli-binary-format raw-in-base64-out \
--function-name my-function \
--invocation-type Event \
--payload '{ "name": "Bob" }' \
response.json

aws dynamodb describe-table --table-name Table1 --no-cli-auto-prompt

aws ec2 describe-instances --output table --region eu-central-1 --profile localstack

