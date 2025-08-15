# Set-Up Instructions
## Redis<br />
*I did end up implementing a caching system with Redis. These instructions walk through how to start up the Redis cache through the terminal and how you can see what keys are stored.*<br />
1. In a terminal, run the command 'redis-server --port 6380' to start the Redis Client up on a clean port.<br />
2. In a new tab on your terminal, use the command 'redis-cli' in order to view the items that will get saved with the application's POST orders endpoint.<br />
- 2.1 Running 'KEYS *' in the same tab with 'redis-cli' will show everything that is currently cached.<br />
- 2.2 'FLUSHDB' can be beneficial for seeing how the GET orders endpoint handles an enriched order being missing from the cache after having been added. (spoilers: it *should* handle it gracefully and even re-add it afterwards).<br />

## Starting the Application<br />
The project is built from Maven and will need a Maven sync / `maven clean install` prior to running.
Within an IDE simply click the start button under the configurations (org.teamviewer.orderenricher.OrderEnricherApplication)/on the main function located in OrderEnricherApplication.java. Everything could kick off, including the creation of an in-memory database with some pre-made values for Customers and Products.
- If you would like to add any further values for testing, these statements can be found at: src/main/resources/data.sql<br />

## Viewing the Database<br />
Once the application has started, you can visit the following URL in order to view the database as things are getting added: http://localhost:8080/h2-console
### Credentials
> **driver class:** org.h2.Driver<br />
> **url:** jdbc:h2:mem:enrichorderdb<br />
> **username:** showcaseUser<br />
> **password:** password<br />

## cURL Samples
Some call samples can be found at the following the the repo: cURLSamples.txt

> GET orders endpoint simply requires the orderId in the path as suggested with ``GET /orders/{orderId}``<br />
> POST orders endpoint requires a body that looks like the following:
>
>  ``{
	"orderId": "value",
  "customerId": "value",
  "productIds": [
		"value",
		"value",
		"value"
	],
	"timestamp": "2024-06-13T15:30:00Z"
}``
> 

# Trade-Offs or Optimization Decisions
I have learned a lot from this project. Some of the items I know I could have made stronger are the Unit Tests and even just making the code slightly cleaner. There were some standards I forgot about that made the code a little muddied as I aimed to fix the standards and get everything working properly. I also know I missed adding in the (optional) bonus points of the filtering. I also did not directly use Docker.<br />

I am proud of having worked in the (optional) bonus points with caching the orders in Redis. From a recent application I worked on, I know the importance of Redis and how drastically it can help reduce the load on highly-critical and highly used applications. I wanted to make sure I added that in (partly too because I wanted to learn how to set it up from the ground up and get it running for a local application).<br />

I also wanted to take the time to include JavaDocs with my applicatikon, ensuring that every public method contained a JavaDoc for better documentation. I also tried to add in regular comments in places to make it clearer what is happening.

I made the decision with the **Timestamp** to go for a ZonedDateTime despite the complication of its format in the request because I've seen major bugs crop up with timestamps that don't include Timezones, so I just thought I'd have a little fun and throw it in. I know the other variables are lacking some consistency as that normally would be defined more in other avenues that would make it more clear what the expectations and contraints would be on those fields. I just tried to keep it simple with those in my implementation for this.

## Tech Constraints Used
- Spring Boot
- Spring Data JPA
- Spring Web
- Redis
- Maven
- Lombok

# Note From the Developer
First off, I just wanted to say thank you for the opprtunity to do this small little project. It was a joy to get to do a project like this after nine years of the work I was doing at GM. This project actually brought back the fond memories of a prtoject I helped modernized a few years back that utilized a lot of these more modern tools such as Lombok and Hibernate. I've greatly missed the power these tools bring to an application.<br />

That being said, I know there is still a lot I could potentially clean up within this project and more robust protections that could be added, some of those items I listed above in the previous section.<br />

Fun Fact: I actually programmed this application with the assistance of TeamViewer. We have an external server set-up for things we don't want/need on our main computers. Thanks to TeamViewer connecting me to that external desktop, I was able to store my IDE over on that computer and not slow my laptop down with its power. So, thanks! :)
