#!/bin/bash

# docker-compose.yml 서비스 이름 정의
service_name="blue"

# Docker Compose 시작 및 Nginx 설정 변경
deploy_and_reload() {
  new_version=$1
  old_version=$2
  old_server=$3
  new_server=$4



  # Docker Compose 실행
  if docker compose up -d --build $new_version; then
    echo "$new_version version started successfully."
    sleep 60  # 성공 시 1분 대기
    # Nginx 설정 파일을 수정하고 리로드
    if docker cp ~/docker/nop_deploy/${new_version}.conf nginx-container:/etc/nginx/conf.d/nop.conf; then
      docker exec nginx-container nginx -s reload
      docker compose down $old_version
      echo ""
    else
      echo "Failed to update Nginx configuration on $new_version."
      exit 1
    fi
  else
    echo "Failed to start $new_version version."
    exit 1
  fi
}

# Docker Compose를 사용하여 지정된 컨테이너가 실행 중인지 확인
is_active=$(docker compose ps -q $service_name)

# 실행 중인 컨테이너가 있는지 확인
if [ -n "$is_active" ]; then
  deploy_and_reload "green" "blue" "nop-backend-container-blue" "nop-backend-container-green"
else
  deploy_and_reload "blue" "green" "nop-backend-container-green" "nop-backend-container-blue"
fi
