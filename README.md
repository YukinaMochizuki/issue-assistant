# Issue Assistant

This is an experimental project focuses on practicing Vaadin platform, GitLab Webhook and Spring Security.

**From [Vaadin official website](https://vaadin.com):**

> Vaadin Flow is a web framework that allows you to write UI 100% in Java without getting bogged down in JS, HTML, and CSS. If you prefer, you can also create layouts in HTML or with a visual designer. 
>
> Your apps run on the server and handle all communication automatically and securely. Building on the strong Java ecosystem, Flow works with your favorite IDEs, tools, and libraries.

I am very excited about writing web app UI with fully OOP, so this project was born to practice this.

## General Information
- Project status: **Forward-looking**, it means that the project may be changed or stopped at any time due to my learning policy.

## Technologies Used
### Development environment
- AdoptOpenJDK 11 with HotSpot JVM
- Software Development Kit Manager: SDKMAN!
- Build System: Gradle 6.8.2
- Database: H2

### Dependencies
- Spring Boot 2.4.3
  - Spring Boot Starter Security
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
- [Vaadin 14 LTS](https://vaadin.com)
- [Picocli](https://picocli.info) - Used to build rich command line applications then connect to communication software
- [TelegramBots](https://github.com/rubenlagus/TelegramBots) - Java library to create bots using Telegram Bots API
- [gitlab4j](https://github.com/gitlab4j/gitlab4j-api) - Planned to be used to create issues, but hasn't been implemented right now
- Libraries or Build tools
  - [Project Lombok](https://projectlombok.org) - Getter, Setter, ToString, AllArgsConstructor and more..
  - [gson](https://github.com/google/gson) - Used for deserialize GitLab Webhook responses


## Demo and screenshot
[![](https://raw.githubusercontent.com/YukinaMochizuki/issue-assistant/master/img/2021-06-24%2015-47-23%20720p.gif)](https://raw.githubusercontent.com/YukinaMochizuki/issue-assistant/master/img/2021-06-24%2015-47-23%20720p.gif)

> The `Telegram UserId` is actually a typo, its correct name should be `chat_id`.

[1080p version](https://github.com/YukinaMochizuki/issue-assistant/blob/master/img/2021-06-24%2015-47-23%201080p.gif)

![](https://i.imgur.com/eMhlHnp.png)
![](https://i.imgur.com/ru46kpX.png)

## Some implementation details
- All of the CLI like command was written in [this package](https://github.com/YukinaMochizuki/issue-assistant/tree/master/src/main/java/tw/yukina/sitcon/issue/assistant/command) as a spring component.
- All of the web app view was written in [this package](https://github.com/YukinaMochizuki/issue-assistant/tree/master/src/main/java/tw/yukina/sitcon/issue/assistant/views).
