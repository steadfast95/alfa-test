# Getting Started

для запуска ./gradlew bootRun или java -jar alfa-0.0.1-SNAPSHOT.jar 

### приложения есть небольшой конфиг( опциональный)
* Для включения использования конфига, необходимо использовать custom.enable-default-values: true
* При включеном конфиге дефолтные значения будут братся из него:
     1. balance - стартовый баланс
     2. currency - валюта счета
     3. active - статус счета( true - активный/ false - не активный)
### Всего доступно 5 ендпоинтов в api
    1. PUT alfa-test/accounts/create - создание счета 
    2. POST alfa-test/accounts/close - закрытие счета
    3. POST alfa-test/accounts/credit - списание
    4. POST alfa-test/accounts/debit - пополнение
    5. GET alfa-test/accounts/{condition}, под condition принимаются значения all,active,closed

### Ответы всех запросов приходят в формате {"body" : object}
