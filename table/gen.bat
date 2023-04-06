
rem gen json file
java -Dorg.slf4j.simpleLogger.defaultLogLevel=info -jar .\tools\excelToCode.jar -i ./excel -l json -o ../json
rem gen java file
java -jar .\tools\excelToCode.jar -i ./excel -l java -o ../src/main/java/com/backinfile/cardRouge/gen/config -p com.backinfile.cardRouge.gen.config -r json
pause