# REFACTORING AND UPDATING NOTICE

This project is old and requires refactoring. It uses a traditional MVC architecture and an old version of Spring.

IT WILL BE GRADUALLY REFACTORED TO A BETTER ARCHITECTURE AND NEWER VERSIONS OF TECHNOLOGY. STILL, CHANGES WILL BE MADE TO DATABASES AND REACTIVE PROGRAMMING WILL BE USED.

REFACTORING STEPS (NOT NECESSARILY IN THIS ORDER):
* Use clean architecture;
* Change database to PostgreSQL or MongoDB;
* Change frontend to React with Next, Svelte or Thymeleaf with HMX;
* Use Spring Reactive.

# University website and CMS - Content Management System

This project implements a university website and CMS. A running version of this project may be found at fatecmogidascruzes.com.br.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You'll need the software described next to run the project:

Compiler
```
JDK 8 or newer
```

HTTP Server
```
Nginx, Apache or equivalent to serve static files (images and PDFs).
```

IDE
```
Any of your preference.
```

LOMBOK
```
The lombok project must be installed in the IDE: https://projectlombok.org/
```

DBMS
```
MySQL 5.8 or newer (may run in older versions as well)
An equivalent DBMS may be configure since the project uses JPA.
```

Google reCAPTCHA
```
A Google ReCAPTCHA account.
```

A Zoho SMTP account
```
A Zoho SMTP account to send password recovery e-mails.
```

### Installing (Instalando)

In order tcaptchao install the project, import it as a maven project in STS.
Moreover, you'll need to define the following environment variables:
* SITE_FATEC_DATABASE_HOST: The host name or IP in which the DBMS is installed.
* SITE_FATEC_DATABASE_PORT: The DBMS service port.
* SITE_FATEC_DATABASE_DATABASE: The database name.
* SITE_FATEC_DATABASE_USER: The database user.
* SITE_FATEC_DATABASE_PASSWORD: The database password.
* SITE_FATEC_STATIC_FILES_PATH: The folder where static files must be saved.
* SITE_FATEC_TEMP_FILES_PATH: The folder where temporary files may be saved.
* SITE_FATEC_RECAPTCHA_SITE: The reCAPTCHA site.
* SITE_FATEC_RECAPTCHA_SECRET: The reCAPTCHA secret key.
* SITE_FATEC_MAIL_FROM: The mail from which messages will be sent.
* SITE_FATEC_MAIL_ACCOUNT_ID: A Zoho SMTP API account id (See https://www.zoho.com/crm/help/api/using-authentication-token.html).
* SITE_FATEC_MAIL_AUTHTOKEN: A Zoho SMTP API auth token (See https://www.zoho.com/crm/help/api/using-authentication-token.html).

## Authors (Autores)

* **Leandro Luque** - *Initial work*

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
