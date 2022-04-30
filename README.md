
# RezhDuma
<br/>

## Архитектура
- Описания:
    - урлов
    - методов
    - запросов
- Примеры
  <br/>
  <br/>
  <br/>
  <br/>

## Описания
Описание того, какие запросы могут обрабатывать определенные страницы  
<br/>
<br/>
|  1. новости / история / документы / проекты |  
| ------ |  
<br/>

>  Новости
>
> `X - id текущей новости`
>
>`GET:` `api/news`   Получение всех новостей (любой)
>
>`GET:` `api/news/X`   Получение одной новости (любой)
>
>`GET:` `api/news/events`   Получение всех событий (любой)
>
>`POST:` `api/news`  Добавление новости (только депутат)
>
>`PATCH:` `api/news/X`  Редактирование новости (только депутат)
>
>`DELETE:` `api/news/X`  Удаление новости (только депутат)
>

| метод| формат тела запроса|формат тела ответа|заголовок запроса|
| ------ | ------    | ------ | ------ |
| GET    | -         | JSON   | -      |
| POST   | form-data | -      |Authorization: token|
| PATCH  | form-data | -      |Authorization: token|
| DELETE | -         |  -     |Authorization: token|
<br/>

>  История думы
>
> `X - id текущей истории`
>
>`GET:` `api/history`   Получение всех историй (любой)
>
>`GET:` `api/history/X`   Получение одной истории (любой)
>
>`POST:` `api/history`  Добавление истории (только депутат)
>
>`PATCH:` `api/history/X`  Редактирование истории (только депутат)
>
>`DELETE:` `api/history/X`  Удаление истории (только депутат)
>
| метод| формат тела запроса|формат тела ответа|заголовок запроса|
| ------ | ------    | ------ | ------ |
| GET    | -         | JSON   | -      |
| POST   | form-data | -      |Authorization: token|
| PATCH  | form-data | -      |Authorization: token|
| DELETE | -         |  -     |Authorization: token|
<br/>

>  Правовые документы
>
> `X - id текущего документа`
>
>`GET:` `api/documents`   Получение всех документов(любой)
>
>`GET:` `api/documents/X`   Получение документа (любой)
>
>`POST:` `api/documents`  Добавление документа (только депутат)
>
>`PATCH:` `api/documents/X`  Редактирование документа (только депутат)
>
>`DELETE:` `api/documents/X`  Удаление документа (только депутат)
>
| метод| формат тела запроса|формат тела ответа|заголовок запроса|
| ------ | ------    | ------ | ------ |
| GET    | -         | JSON   | -      |
| POST   | form-data | -      |Authorization: token|
| PATCH  | form-data | -      |Authorization: token|
| DELETE | -         |  -     |Authorization: token|
<br/>

>  Проекты
>
> `X - id текущего проекта`
>
>`GET:` `api/projects`   Получение всех проектов(любой)
>
>`GET:` `api/projects/X`   Получение проекта (любой)
>
>`POST:` `api/projects`  Добавление проекта (только депутат)
>
>`PATCH:` `api/projects/X`  Редактирование проекта (только депутат)
>
>`DELETE:` `api/projects/X`  Удаление проекта (только депутат)
>
| метод| формат тела запроса|формат тела ответа|заголовок запроса|
| ------ | ------    | ------ | ------ |
| GET    | -         | JSON   | -      |
| POST   | form-data | -      |Authorization: token|
| PATCH  | form-data | -      |Authorization: token|
| DELETE | -         |  -     |Authorization: token|
<br/>


|  2. вход /  регистрация / обновление токена|
| ------ |
>  Вход  
>  `POST:` `api/login` Аутентификация (житель или депутат)

>  Регистрация                   
>`POST:` `api/registration`  Создание нового аккаунта  (любой)
> После регистрации перебрасивает на страницу входа,  
> появляется уведомление, что войти можно только после подтверждения почты

>  Обновление токена  
>  `GET:` `api/token/refresh`  Получение токена по рефреш токену,  
>   происходит автоматически по истечении срока жизни токена
>   (житель или депутат)

>  Выход  
>  Выполняется на клиенте путем удаления его токенов

>
| тип|формат тела запроса|формат тела ответа|заголовок запроса|
| ------             | ------    | ------       | ------                     |
| Вход               | form-data | JSON(tokens) |-                           |
| Регистрация        | JSON      | -            |-                           |
| Обновление токена  | -         |JSON(token)   |Authorization: refresh token|
<br/>  
<br/>
<br/>

## Примеры  

Примеры запросов на получение/изменение данных

 <br/>

- Новости

    - Получение всех новостей 
     ```
     GET: api/news
     ```

    - Получение одной новостей с id = X
     ```
     GET: api/news/X
     ```
     
     - Получение всех событий
     ```
     GET: api/news/events
     ```
     
     - Добавление новости
     ```
     POST: api/news
        Headers: 
            Authorization: token
        Body:
            title: "Тут заголовок новости"
            text: "Тут основной текст новости"
            evenet: true (true - эта новсть событие, иначе - false)
            files: File.png (Тут прикрепляем файлы)
     ```
     
     - Редактирование новости с id = X
     ```
     PATCH: api/news/X
        Headers: 
            Authorization: token
        Body: 
            title: "Тут заголовок новости"
            text: "Тут основной текст новости"
            evenet: true (true - эта новсть событие, иначе - false)
            files: File.png (Тут прикрепляем файлы
        (Поля не обязательны(можно поменть только title, например, и только его и отправить))
     ```
     
     - Удаление новости с id = X
     ```
     DELETE: api/news/X
        Headers: 
            Authorization: token
     ```

 <br/>
 <br/> 

- История
    - Получение всех историй
   ```
   GET: api/history
   ```

    - Получение одной истории с id = X
   ```
   GET: api/history/X
   ```

    - Добавление истории
   ```
   POST: api/history
      Headers: 
          Authorization: token
      Body:
          title: "Тут заголовок истории"
          text: "Тут основной текст истории"
          files: File.png (Тут прикрепляем файлы)
   ```

    - Редактирование истории с id = X
   ```
   PATCH: api/history/X
      Headers: 
          Authorization: token
      Body: 
          title: "Тут заголовок истории"
          text: "Тут основной текст истории"
          files: File.png (Тут прикрепляем файлы
      (Поля не обязательны(можно поменть только title, например, и только его и отправить))
   ```

    - Удаление истории с id = X
   ```
   DELETE: api/history/X
      Headers: 
          Authorization: token
   ```


 <br/>
 <br/> 

- Проекты
    - Получение всех проектов
   ```
   GET: api/projects
   ```

    - Получение одного проекта с id = X
   ```
   GET: api/projects/X
   ```

    - Добавление проекта
   ```
   POST: api/projects
      Headers: 
          Authorization: token
      Body:
          title: "Тут заголовок проекта"
          text: "Тут основной текст проекта"
          files: File.png (Тут прикрепляем файлы)
   ```

    - Редактирование проекта с id = X
   ```
   PATCH: api/projects/X
      Headers: 
          Authorization: token
      Body: 
          title: "Тут заголовок проекта"
          text: "Тут основной текст проекта"
          files: File.png (Тут прикрепляем файлы
      (Поля не обязательны(можно поменть только title, например, и только его и отправить))
   ```

    - Удаление проекта с id = X
   ```
   DELETE: api/projects/X
      Headers: 
          Authorization: token
   ```


 <br/>
 <br/> 

- Документы
    - Получение всех документов
   ```
   GET: api/documents
   ```

    - Получение одного документа с id = X
   ```
   GET: api/documents/X
   ```

    - Добавление документа
   ```
   POST: api/documents
      Headers: 
          Authorization: token
      Body:
          title: "Тут заголовок документа"
          text: "Тут основной текст документа"
          files: File.png (Тут прикрепляем файлы)
   ```

    - Редактирование документа с id = X
   ```
   PATCH: api/documents/X
      Headers: 
          Authorization: token
      Body: 
          title: "Тут заголовок документа"
          text: "Тут основной текст документа"
          files: File.png (Тут прикрепляем файлы
      (Поля не обязательны(можно поменть только title, например, и только его и отправить))
   ```

    - Удаление документа с id = X
   ```
   DELETE: api/documents/X
      Headers: 
          Authorization: token
   ```

<br/>
<br/>

- Вход
  ```
   POST: api/login
      Body: 
          email: "тут почта юзера"
          passwords: "тут пароль"
          
     Ответ на запрос (json): 
      {
          "access_token": "тут токен авторизации",
          "refresh_token": "тут токен обновления"
      }
  
  ```


<br/>
<br/>

- Регистрация
  ```
     POST: api/registration
    	Body(json): 
          {
              "email" : "почта юзера",
              "password": "его пароль",
              "phone": "номер телефона",
              "firstName": "имя",
              "lastName": "фамилия",
              "patronymic": "отчество"  
          }
  ```

<br/>
<br/>

- Обновление токена
  ```
     GET: api/token/refresh
     	Headers: 
        	Authorization: refresh token
    	Ответ на запрос (json):
          {
                "access_token": "тут новый токен авторизации"
          }
  ```





