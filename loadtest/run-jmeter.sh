#!/bin/bash

# 타임스탬프 생성 (예: 20240507_114523)
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

# 파일 경로 설정
JMX_FILE="load-test.jmx"
JTL_FILE="result_${TIMESTAMP}.jtl"
REPORT_DIR="report_${TIMESTAMP}"

# JMeter 테스트 실행
echo "JMeter 테스트 실행 중..."
jmeter -n -t "$JMX_FILE" -l "$JTL_FILE" -e -o "$REPORT_DIR"

echo "테스트 완료! 리포트 경로: $REPORT_DIR"