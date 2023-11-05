# INTEGRATION TETS ROBOT FOR BLANKFACTOR DIGITAL BANK - MARQETA

This is the integration testing robot for the Blankfactor Digital Bank emulator
designed for Marqeta.

This robot carries out JSON Schema validations of Marqeta responses. This helps
detect any changes and assess their impact on the Blankfactor Digital Bank.

---

## 🧰 TECH STACK

### Requirements

* Java 17
* Maven
* Serenity BDD
* Cucumber
* RestAssured
* JUnit4
* NodeJS (optional)
* SonarQube (optional)

### IDE Plugins

* JUnit
* Cucumber-java
* Gherkin
* Maven
* Sonarlint
* Lombok

---

## 📗 DOCUMENTATION

* [Confluence page](https://blankfactor.atlassian.net/wiki/spaces/Marqueta/pages/603619801/Project+Overview)
* [Jira Board](https://blankfactor.atlassian.net/jira/software/projects/CMQT/boards/77)
* [Development repository](https://us-east-1.console.aws.amazon.com/codesuite/codecommit/repositories/DigitalBank/browse/refs/heads/develop?region=us-east-1)
* [Integration tests repository](https://us-east-1.console.aws.amazon.com/codesuite/codecommit/repositories/DigitalBankQA/browse?region=us-east-1)

---

## 🚀 EXECUTION

Prior execution you must create **System variables** or a `.env` file in the
root of the project or create some environment variables with the following
values:

```
MARQETA_USERNAME=myusername
MARQETA_PASSWORD=mypassword
BF_MARQETA_PUBLIC_URL=https://url.public.com
BF_CORE_PUBLIC_URL=https://url.public.or.local.com
```

Prior to launch executions you need to have the Blankfactor Bank service (the
main project) running locally (with ngrok) or in AWS. The CORE url would be a
public or local url.

**NOTE:** To local launch you need to
follow [these instructions](https://us-east-1.console.aws.amazon.com/codesuite/codecommit/repositories/DigitalBank/browse/refs/heads/develop?region=us-east-1).

### Run all tests

```bash
mvn clean verify
```

### Run a specific test

You have two options. You can do it through the command line:

```bash
mvn clean verify -Dcucumber.options="--tags @tag"
```

You can do it through the IDE opening the feature file and clicking on the play
button.

### Open the test report

After the execution with the command line, you can open the report in the
following
path [target/site/serenity/index.html](target/site/serenity/index.html).

---

## 🏆 DEVELOPER GOOD PRACTICES

### Run code style linters

Linting the code is important to keep the code clean and readable. It is
important to keep in mind that:

> The ratio of time spent reading versus writing is well over 10 to 1. We are
> constantly reading old code as part of the effort to write new code. …making
> it easy to read makes it easier to write.

_Robert C. Martin (a.k.a Uncle Bob)_

To run the linter for Java, execute the following command:

````bash
mvn spotless:apply
````

Sometime, errors in feature files causes errors or confusions in the Serenity
Report. You can prevent this running the Gherkin linter:

````bash
./scripts/gherkin-lint.sh
````

To run the checker for Gherkin, execute the following command:

````bash
mvn serenity:check-gherkin
````

---

### Run static code analysis

It is a good practice to run static code analysis tools to detect possible bugs
or code smells prior to ask for a merge permission (post a PR). You can run the
following command to run the analysis:

````bash
mvn clean test sonar:sonar
````

Report will be located at http://localhost:9000

**NOTE:** You need to have SonarQube server running locally or in your Docker.

### Gitmoji Commit Messages

We use [Gitmoji](https://gitmoji.dev/) to add emojis to our commit messages and
make it easier to identify the purpose or intention of a commit.

---

## ✏️ Serenity + Screenplay pattern

**Want to learn more?** For more information about Serenity BDD, you can read
the [Serenity BDD Book](https://serenity-bdd.github.io/theserenitybook/latest/index.html),
the official online Serenity documentation source. Other sources include:

* [Byte-sized Serenity BDD](https://www.youtube.com/channel/UCav6-dPEUiLbnu-rgpy7_bw/featured)
  tips and tricks about Serenity BDD
* [Serenity BDD Blog](https://johnfergusonsmart.com/category/serenity-bdd/)
  regular articles
  about Serenity BDD
* [The Serenity Dojo](https://www.serenity-dojo.com) - Tailored BDD and Test
  Automation Training and Mentoring
