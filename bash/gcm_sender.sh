server_api_key='server api key를 입력합니다.'
token='Instance ID의 token을 입력합니다.'
curl --header "Authorization: key=$server_api_key" \
--header Content-Type:"application/json" \
https://gcm-http.googleapis.com/gcm/send \
-d "{\"data\":{\"title\":\"saltfactory GCM demo\",\"message\":\"Google Cloud Messaging 테스트\"},\"to\":\"$token\"}
