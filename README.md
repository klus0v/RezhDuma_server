
# RezhDuma
<br/>


##### Сайт на хостинге:


*[API](http://51.250.18.11:8080):* `http://51.250.18.11:8080`  
*[сайт](http://rezh.ml):* `http://rezh.ml`

  <br/>

##### Локальный запуск:

[Актуальные сборки сервера](https://drive.google.com/drive/folders/1mtPKIPZuP1ubZwE24QjZr1A3uuzA7kiZ?usp=sharing)

`После запуска, в БД в талблицу roles добавить поля: "USER", "ADMIN"`  
`Роль "ADMIN" к пользователю добавляется вручную через БД`

<br/>
<br/>

### Общая схема работы приложения

![alt text](https://github.com/DanilKlus/TgBot/blob/main/api.jpg?raw=true)

## Архитектура
1. Новости и прочее
2. Регистрация, логин
3. Обращения
4. Голосования
5. Поиск по сайту
6. ЛК юзера

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
>[page= count=] + параметры для пагинации
>
>`GET:` `api/news?find=Слово для поиска`    Поиск по новостям  
>[page= count=] + параметры для пагинации
>
>`GET:` `api/news/X`   Получение одной новости (любой)
>
>`GET:` `api/news/events`   Получение всех событий (любой)  
>[page= count=] + параметры для пагинации
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
>[page= count=] + параметры для пагинации
>
>`GET:` `api/history?find=Слово для поиска`    Поиск по историям  
>[page= count=] + параметры для пагинации
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
>[page= count=] + параметры для пагинации
>
>`GET:` `api/documents?find=Слово для поиска`    Поиск по документам  
>[page= count=] + параметры для пагинации
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
>[page= count=] + параметры для пагинации
>
>`GET:` `api/projects?find=Слово для поиска`    Поиск по проектам  
>[page= count=] + параметры для пагинации
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
| Вход               | form-data | JSON(tokens, user info) |-                           |
| Регистрация        | JSON      | -            |-                           |
| Обновление токена  | -         |JSON(token, user info)   |Authorization: refresh token|
<br/>  
<br/>
<br/>






|  3. обращения |
| ------ |
`X - id пользователя`                  
`Y - id обращения`
>  Доступно всем  
>  `GET:` `api/appeals/popular` Получения частых вопросов  
> `find=Поиск&type=Тип&district=Район&topic=Сфера` - доступные параметры  
> [page= count=] + параметры для пагинации


> Доступно жителю  
> `GET:` `api/appeals/user/Y`  Получение одного своего обращения
>
>`GET:` `api/appeals/user`  Получение всех свох обращений  
> `find=Поиск&type=Тип&district=Район&topic=Сфера` - доступные параметры  
> [page= count=] + параметры для пагинации
>
>`GET:` `api/appeals/user?answered=true`  Получение свох рассмотренных обращений    
>[page= count=] + параметры для пагинации
>
>`GET:` `api/appeals/user?answered=false`  Получение свох не рассмотренных обращений  
>[page= count=] + параметры для пагинации
>
>`DELETE:` `api/appeals/user?appeal=Y` Удалить свое обращение  
>`POST:` `api/appeals/user` Добавить обращение  
>`PATCH:` `api/appeals/user?appeal=Y` Отредактировать свое обращение
>

>  Доступно депутату   
>  `GET:` `api/appeals/admin/Y` Получить одно обращение
>
>  `GET:` `api/appeals/admin` Получить все обращения  
>  [page= count=] + параметры для пагинации
>
>  `GET:` `api/appeals/admin?find=Поиск&answered=true&type=ТИП&topic=СФЕРА&district=РАЙОН`  
>  [page= count=] + параметры для пагинации    
>   Получить обращения с фильтрами (парметры не обязательны, можно использовать любую комбинацию параметров)
>
>  `PATCH:` `api/appeals/admin/Y`  Ответить на обращение


>
| тип|формат тела запроса|формат тела ответа|заголовок запроса|
| ------             | ------    | ------       | ------                     |
| GET(для частых вопросов)| -         | JSON         |-                           |
| GET                | -         | JSON         |Authorization: token        |
| DELETE             | -         | -            |Authorization: token        |
| POST               | form-data | -            |Authorization: token        |
| PATCH              | form-data | -            |Authorization: token        |

<br/>  
<br/>
<br/>




|  4. голосования |
| ------ |
>  Доступно всем  
>  `GET:` `api/votes/X` Получить голосование с id=X
>
>  `GET:` `api/votes` Получить все голосования
>  [page= count=] + параметры для пагинации; [find=] параметр для поиска
>

> Доступно жителю                   
>`PATCH:` `api/votes/user?vote=X` Ответить юзеру на голосование с id=X,
>

>  Доступно депутату   
>  `POST:` `api/votes/admin` Создать голосование  
>  `DELETE:` `api/votes/admin/X`  Удалить голосование c id=X


>
| тип|формат тела запроса|формат тела ответа|заголовок запроса|
| ------             | ------    | ------       | ------                     |
| GET                | -         | JSON         |-        |
| PATCH              | JSON | -            |Authorization: token        |
| POST               | JSON | -            |Authorization: token        |
| DELETE             | -         | -            |Authorization: token        |


<br/>  
<br/>
<br/>


|  5. поиск |
| ------ |

>`GET:` `api/search?find=X` Поиск по сайту, X - то, что нужно найти  
>[page= count=] + параметры для пагинации (пагинируется гаждый объект отдельно)  
>Доп. параметры:   
>news=true, projects=true, documents=true, history=true, appeals=true, ballots=true  
>(они не обязательны, можно их комбинировать для фильтрации поиска)


| тип|формат тела запроса|формат тела ответа|заголовок запроса|
| ------             | ------    | ------       | ------                     |
| GET                | -         | JSON         |-        |


<br/>  
<br/>
<br/>



| 5. ЛК юзера |
|-------------|

>`GET:` `api/users/profile` Получить данные о юзере  

> `PUT:` `api/users/edit/profile` Отредактировать данные юзера  
 
> `PATCH:` `api/users/edit/password` Изменить пароль юзера


| тип   | формат тела запроса | формат тела ответа | заголовок запроса   |
|-------|---------------------|--------------------|---------------------|
| GET   | -                   | JSON               | Authorization: token |
| PUT   | JSON                | JSON               | Authorization: token |
| PATCH | form-data           | -                  | Authorization: token |

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
<br/>

- Пример токена отправки токена

  Headers:
  Authorization:
  `Rezh "token"`

  (при каждом запросе перед токеном добавлять:  
  "Rezh ")


<br/>
<br/>
- Обращения

<br/>

- Доступно всем
   ```
   GET: api/appeals/popular
   ```

- Доступно жителям  (без тела запроса)
   ```
        [ везде: Headers: Authorization: 'Rezh {token}' ]
     
     GET: api/appeals/user/X
     GET: api/appeals/user/X?answered=true
     GET: api/appeals/user/X?answered=false
     DELETE: api/appeals/user/X?appeal=Y
   ```
- Доступно жителям  (тело запроса form-data)
   ```
   POST: api/appeals/user/X
     
   Headers: 
       Authorization: 'Rezh {token}'
     
   тело запроса (form-data):
       type: "тип обращения(вопрос, жалоба и прочее)"
      district: "район"
      topic: "сфера обращения"
      text: "сам текст обращения"
      files: файлы(можно несколько)
   ```

   ```
   PATCH: api/appeals/user/X?appeal=Y
     
   appeal = id обращения
     
   Headers: 
       Authorization: 'Rezh {token}'
     
   тело запроса (form-data):
       type: "тип обращения(вопрос, жалоба и прочее)"
      district: "район"
      topic: "сфера обращения"
      text: "сам текст обращения"
      files: файлы(можно несколько)
   ```

- Доступно депутату  (без тела запроса)
   ```
        [ везде: Headers: Authorization: 'Rezh {token}' ]
     
     GET: api/appeals/admin/Y
     GET: api/appeals/admin
     GET: api/appeals/admin?type=ТИП&topic=СФЕРА&district=РАЙОН
   ```
- Доступно депутату  (тело запроса form-data)
   ```
   PATCH: api/appeals/admin/Y
     
   Headers: 
       Authorization: 'Rezh {token}'
     
   тело запроса (form-data):
       id: id депутата (который отвечает на обращение)
      response: "текст ответа"
      frequent: true/false (флажок для отметки вопроса как частый)
   ```

- Голосования

<br/>

- Доступно всем
   ```
   GET: api/votes/X  
   GET: api/votes
   ```

- Доступно жителям
   ```
   PATCH: api/votes/user/Y?vote=X
   
   Y - id юзера
   vote=X, параметр, где X - id голосования
     
   Headers: 
       Authorization: 'Rezh {token}'
     
   тело запроса (JSON):
   [id1, id2, id3]
   	(массив с id ответов(answers ID) за которые проголосовал житель)
       
   ```



- Доступно депутату  (без тела запроса)
   ```
     DELETE: api/votes/admin/Y
     Y - id голосования
     
     Headers: Authorization: 'Rezh {token}'
   ```
- Доступно депутату  (тело запроса JSON)
   ```
   POST: api/votes/admin
     
   Headers: 
       Authorization: 'Rezh {token}'
     
   тело запроса (JSON):
     
  	{
      "topic": "Тема опроса",
      "expirationDate": "2022-08-22T22:41:38.502031400",
      "questions": [
          {
              "question": "Вопрос 1",
              "checkbox" false,
              "answers": [
                  {
                      "answer": "ответ к нему"
                  },
                  {
                      "answer": "и еще ответ к нему"
                  }
              ]
          },
          {
              "question": "Вопрос 2",
              "checkbox" true,
              "answers": [
                  {
                      "answer": "ответ на вопрос 2"
                  },
                  {
                      "answer": "еще ответ на вопрос 2"
                  },
                  {
                      "answer": "и еще ответ на вопрос 2"
                  },
              ]
          }
      ]
	}
     
   ```

- Поиск
  ```
     POST: api/search?news=true&appeals=true&ballots=true&find=что-нибудь
     
     Пример поиска по новостям, обращениям и голосованиям/опросам
     
     Вернет Json, с массивами всех найденных объектов
    	
  ```

<br/>
<br/>

- ЛК юзера
  - Получить данные юзера
    ```
       GET: api/users/profile
     
       response(json):
        {
          "id": 1,
          "email": "почта",
          "phone": "телефон",
          "firstName": "Имя",
          "lastName": "Фамилия",
          "patronymic": "Отчество",
         "roles": [
             "Роль 1",
             "Роль 2"
          ]
        }
    	
    ```
  - Отредактировать данные юзера
    ```
    Headers: 
       Authorization: 'Rezh {token}'
    
       PUT: api/users/edit/profile
       
        request (json) :
        {
          "email": "почта",
          "phone": "телефон",
          "firstName": "Имя",
          "lastName": "Фамилия",
          "patronymic": "Отчество"
        }
    	
    ```
- Изменить пароль юзера
  ``` 
  Headers: 
       Authorization: 'Rezh {token}'
     
     PATCH: api/users/edit/password
       
      request (form-data) :
          password: "Тут старый пароль"
          newPassword: "Тут новый пароль"
  ```

<br/>
<br/>










