# Api urls

## URLS

```ts
export const API_CONFIG = {
  BASE_URL: 'http://localhost:7278/',
  ENDPOINTS: {
    AUTH: {
      LOGIN: 'auth/login',
      REGISTER: 'auth/register',
      CHECK: 'auth/check',
      REFRESH: 'auth/refresh',
    },
    GLOBALS: {
      BASE_URL: 'globals/',
      GET_PROFESSIONALS: 'globals/get-professionals',
      ACCEPT_PROBLEM: 'globals/accept-problem', 
      REFUSE_PROBLEM: 'globals/refuse-problem', 
      CHANGE_PRIORITY: 'globals/change-priority',
    },
    CLIENT: {
      BASE_URL: 'client/',
      MAKE_APPLICATION: 'client/make-application',
      GET_CONCLUSION: 'client/conclusion',
      ACCEPT_CONCLUSION: 'client/accept-conclusion',
      REFUSE_CONCLUSION: 'client/refuse-conclusion',
    },
    PROFESSIONAL: {
      BASE_URL: 'professional/',
      GET_PROBLEMS: 'professional/get-problems',
      MAKE_JUDGMENT: 'professional/make-judgment',
    },
    SUPPORT_CENTER: {
      BASE_URL: 'support-center/',
      SET_PROFESSIONAL: 'support-center/set-professional',
      CLOSE_PROBLEM: 'support-center/close-problem',
    },
    ADMIN: {
      BASE_URL: 'admin/',
      GET_USERS: 'admin/get-users',
    },
  },
};
```

## Описание urls

**Порт приложения `7278`**

### Базовый адрес

`http://localhost:7278/`

Базовый адрес по которому работает все приложение.

### AUTH

Адреса в этом блоке отвечают за авторизацию, аутентификацию, регистрацию. К этим запросам имеют доступ все пользователи

#### auth/login

**Метод**: `GET`

**Описание**: Адрес который реализует логику форму логина

***Отправка***

На сервер будет приходить логин или email, пароль

```ts
email:string || login:string
password:string
```

***Принятие***

Клиент ждет, full name это Иванов Иван Иванович(нету разделения на фамилию и имя, отчество)

```ts
full_name:string
email:string
birth_date:string
contact_number:string
address:string
```

****

#### auth/register

**Метод**: `POST`

**Описание**: Адрес который реализует логику форму регистрации

***Отправка***

Данные для регистрации

```ts
login:string
email:string
full_name:string
birth_date:string
contact_number:string
address:string
```

***Принятие***

Http коды что все хорошо или плохо

****

#### auth/check

**Метод**: `GET`

**Описание**: Адрес который реализует логику проверки на аутентификацию пользователя, по access token.

***Отправка***

Куки

***Принятие***

HTTP коды

****

#### auth/refresh

**Метод**: `GET`

**Описание**: Адрес который реализует логику проверки refresh токена на сервере

***Отправка***

Куки

***Принятие***

HTTP коды

****

### GLOBALS

Этот блок описывает адреса которые доступны в нескольких ролях, вынесены сюда что бы не дублировать логику

#### globals/get-professionals

**Метод**: `GET`

**Доступ**: Клиенты, Центры

**Описание**: Получение профессионалов в виде списка

***Отправка***

Запрос на получение

***Принятие***

fk_center прислать к какому центру привязан

```ts
email:string
full_name:string
contact_number:string
info:string
professionals_rating:number
fk_center: string
center_rating:number
```

****

#### globals/accept-problem

**Метод**: `POST`

**Доступ**: Профессионалы, Центры

**Описание**: Сущность подтверждает что будет решать проблему и статус решения проблемы переходит с `idle` на `processing`.

***Отправка***

Код 200 что принимаем проблему, будем работать

****

#### globals/refuse-problem

**Метод**: `POST`

**Доступ**: Профессионалы, Центры

**Описание**: Сущность отклоняет что будет решать проблему и статус решения проблемы переходит с `idle` на `rejected`.

***Отправка***

Проблему мы не решаем, сервер удаляет с бд проблему

***Принятие***

Прислать пользователю сообщение, мы не можем рассмотреть вашу проблему

****

#### globals/change-priority'

**Метод**: `POST`

**Доступ**: Профессионалы, Центры

**Описание**: Сущность меняет приоритетность проблемы.

#### globals/get-problems

**Метод**: `GET`

**Доступ**: Профессионалы, Центры

**Описание**: Сущность получает проблемы. В зависимости от роли получение пользователей разная, центр получает все проблемы которые направлены, а профессионал получает проблемы которые за ним закреплены

***Принятие***

```ts
Problem = {
  title:string
  description:string
  received_date:string
}

Client = {
  full_name:string
  email:string
  birth_date:string
  contact_number:string
  address:string
}
```

****

### CLIENT

В этом блоке доступ имеет к запросам имеет только клиент

#### client/make-application

**Метод**: `POST`

**Описание**: Пользователь создает заявку

***Отправка***

```ts
title:string
description:string
received_date:string
```

****

#### client/conclusion

**Метод**: `GET`

**Описание**: Пользователь получает заключение

***Принятие***

```ts
text_info:string
Professional = {
  full_name:string
  email:string,
  contact_number:string
}
```

****

#### client/accept-conclusion, client/refuse-conclusion

**Метод**: `GET`

**Описание**: Пользователь соглашается или отклоняет заключение

***Отправка***

Не согласен с заключением, удаление заключение из бд

****

#### client/rate/{professional || support center}

**Метод**: `GET`

**Описание**: Пользователь ставит оценку профессионалу или центру

***Отправка***

```ts
rating:number
```

****

### PROFESSIONAL

В этом блоке доступ имеет доступ только профессионал

#### professional/make-judgment

**Метод**: `POST`

**Описание**: Профессионал пишет заключение

***Отправка***

```ts
text_info:string
```

****

#### professional/get-user

**Метод**: `GET`

**Описание**: Получение пользователя, профессионал будет получать информацию о пользователе

***Принятие***

```ts
  full_name:string
  email:string
  birth_date:string
  contact_number:string
  address:string
```

****

### SUPPORT_CENTER

В этом блоке доступ имеет доступ только центры

#### support-center/set-professional

**Метод**: `POST`

**Описание**: Центр закрепляет профессионала за клиентом

#### support-center/close-problem

**Метод**: `PUT`

**Описание**: Центр завершает проблему и ставить статус `fulfilled`

#### support-center/{add/remove}/professionals

**Метод**: `POST`

**Описание**: Добавление или удаление профессионала с центра

***Отправка***

id профессионала

```ts
id:number
```

### ADMIN

#### admin/get-users

**Метод**: `GET`

**Описание**: Получение всех пользователей

```ts
  login:string
  full_name:string
  email:string
  address:string
  role:string
  isBaned:boolean
```

#### admin/action/id

**Метод**: `PUT`

**Описание**: Взаимодействие с пользователями, action это что сделать надо с пользователем. Actions: `ban, unBan, setAdmin, removeAdmin`

#### admin/add-support-center

**Метод**: `POST`

**Описание**: Админ добавляет центр

***Отправка***

```ts
name:string
address:string
contact_number:string
email:string
avatar:bytea
```
