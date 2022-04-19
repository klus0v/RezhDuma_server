
# RezhDuma


## Архитектура






- Описание урлов
- Описание методов
- Описание запросов
- Описание нестандартных случаев


## URL's


| адрес | описание |
| ------ | ------ |
| api/news | Новости  |
| api/history | История думы |
| api/documents | Правовые документы |
| api/projects | Проекты |
| ------ | ------ |
| api/appeals | Обращения|
| api/votings | Опросы|
| api/comments | Коментарии |
| ------ | ------ |
| api/signin | Вход |
| api/signup | Регистрация |
| api/admin | ЛК админа |
| api/user | ЛК юзера |
| ------ | ------ |


## HTTP methods

Описание того, какие запросы могут обрабатывать определенные страницы

|  1 |
| ------ |
>  Новости   
`GET:` `api/news`   Получение всех новостей (любой)  
`GET:` `api/news?id=X`   Получение одной новости (любой)  
`POST:` `api/news`  Добавление новости (депутат)  
`PUT:` `api/news?id=X`  Редактирование новости (депутат) 
 `DELETE:` `api/news?id=X`  Удаление новости (депутат) 

>  История думы
>  `GET:` `api/history`   Получение всех историй (любой)  
`GET:` `api/history?id=X`   Получение одной истории (любой)  
`POST:` `api/history`  Добавление истории (депутат)
`PUT:` `api/history?id=X`  Редактирование истории (депутат)
`DELETE:` `api/history?id=X`  Удаление истории (депутат)

>  Правовые документы
`GET:` `api/documents`   Получение всех документов(любой)  
`GET:` `api/documents?id=X`   Получение документа (любой)  
`POST:` `api/documents`  Добавление документа (депутат)
`PUT:` `api/documents?id=X`  Редактирование документа (депутат)
`DELETE:` `api/documents?id=X`  Удаление документа (депутат)

>  Проекты      
`GET:` `api/projects`   Получение всех проектов(любой)  
`GET:` `api/projects?id=X`   Получение проекта (любой)  
`POST:` `api/projects`  Добавление проекта (депутат)
`PUT:` `api/projects?id=X`  Редактирование проекта (депутат)
`DELETE:` `api/projects?id=X`  Удаление проекта (депутат)


|  2 |
| ------ |

>  Обращения
`GET:` `api/appeals?frequent=true?type=X` Получение частозадоваемых обращений (житель)  [type - тип обращения: вопрос, заявка, ..]
`POST:` `api/appeals` Отправить обращение (житель)  
`GET:` `api/appeals?user_id=X` Получение всех своих обращений (житель)  
`GET:` `api/appeals?user_id=X&id=X` Получение одного своего обращения (житель)  
`PUT:` `api/appeals?user_id=X&id=X` Редактирование своего обращения (житель)  
`DELETE:` `api/appeals?user_id=X&id=X` Удаление своего обращения (житель) 
`GET:` `api/appeals` Получение всех обращений (депутат)  
`GET:` `api/appeals?type-X&district=X&topic=X` Получение всех обращений с фильтрами (депутат)    
`GET:` `api/appeals?id=...` Получение одного обращения (депутат)                              
`PUT:` `api/appeals?id=...`  Добавление ответа на обращение (депутат)        

>  Опросы
`GET:` `api/votings` Получение всех опросов (любой)   
`GET:` `api/votings?id=...` Получение одного опроса (любой)                              
`POST:` `api/votings` Создание нового опроса (депутат) 
`PUT:` `api/votings?id=...`  Добавление ответа на опрос (житель)        
`DELETE:` `api/votings?id=...`  Удаление опроса (депутат)

>  Коментарии      
`GET:` `api/comments`  Получение коментарив (житель)                           
`POST:` `api/comments`  Создание нового коментария (житель)
`DELETE:` `api/comments`    Удаление коментария (admin)

|  3 |
| ------ |
>  Вход      
`GET:` `api/signin`   Авторизация (житель / депутат)                         

>  Регистрация      
`GET:` `api/signin` Получение статуса регистрации  (житель / депутат)                  
`POST:` `GET:` `api/signin`  Создание нового аккаунта  (житель / депутат)                          

>  ЛК админа      
`GET:`   Получение списка вопросов / заявок 

>  ЛК юзера      
`GET:`   Получение своего списка вопросов / заявок 






