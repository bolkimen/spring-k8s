aws s3 mb s3://test --profile localstack
aws s3 ls --profile localstack

aws s3api list-buckets --query "Buckets[].Name" --profile localstack
aws s3api list-buckets --query "Buckets[]" --profile localstack

aws lambda list-functions --region=eu-central-1 --profile=localstack
aws dynamodb list-tables --region=eu-central-1 --profile localstack

aws dynamodb scan --table-name Files --limit 1 --region=eu-central-1 --profile localstack

aws dynamodb scan \
--region=eu-central-1 --profile localstack \
--table-name Files \
--filter-expression "FileName = :name" \
--expression-attribute-values '{":name":{"S": file.txt"}}' \
--page-size 100  \
--debug

aws lambda get-function-configuration --function-name arn:aws:lambda:eu-central-1:000000000000:function:upload_trigger_lambda --profile localstack

aws lambda invoke --profile=localstack \
--cli-binary-format raw-in-base64-out \
--function-name arn:aws:lambda:eu-central-1:000000000000:function:upload_trigger_lambda \
--invocation-type Event \
--payload '{ "name": "Bob" }' \
response.json

awslocal kinesis list-streams

aws lambda invoke --profile=localstack \
--cli-binary-format raw-in-base64-out \
--function-name my-function \
--invocation-type Event \
--payload '{ "name": "Bob" }' \
response.json

aws dynamodb describe-table --table-name Table1 --no-cli-auto-prompt

aws ec2 describe-instances --output table --region eu-central-1 --profile localstack

https://docs.getmoto.org/en/stable/docs/getting_started.html

https://github.com/localstack-samples
