## Aerospike Client Exp Demo

## Introduction:
This README file provides an overview of the Aerospike Expression module. The Expression module helps to construct and parse Aerospike expressions, which can be used in filter predicates, aggregation, and query projections.


## ExpBuilder Class:
The Exp class contains nested Exp classes. The ExpBuilder class is used to build nested object references of the Exp class. Also, a Custom Exp class has been created which acts as a temporary DTO to convert the provided JSON to a Java object. It also contains the recursion logic to build nested Exp classes.


## Static Methods of Aerospike Exp Class:
The static methods of the Aerospike Exp class have been divided into five parts:

	1.It will create Exp based on Datatype provided.
	2.It will create an Exp object based on the value provided.
	3.It takes two arguments and creates an Exp based on that.
	4.It takes a single argument and creates an Exp based on that.
	5.It takes a variable number of arguments and creates an Exp object based on that.


- [Exp Java Docs](https://javadoc.io/doc/com.aerospike/aerospike-client/latest/com/aerospike/client/exp/Exp.html)
---
### Expression Examples:
An example of an Aerospike expression is "email == 'example@gmail.com'". When we deserialize this example, it looks like the JSON file below:

	Expression
		email == "example@gmail.com"
	
	Deserialized json

		{
		  "exps": [
			{
			  "name": "email",
			  "type": "STRING"
			},
			{
			  "val": "example@gamil.com"
			}
		  ],
		  "cmd": 4
		}	

In the above JSON file, "cmd": 4 means "equal to". They provide different numbers for each enum Operation. When we provide the JSON from an API request, we have to change that cmd 4 to "cmd":"EQ" so, It is in a more readable form.

---
The following is an example of an Aerospike expression:

### Expression Examples
x==10 and y=="abc" and (z==1 or z==2)

	Java Syntax
	Exp.and(
		Exp.eq(Exp.intBin("x"), Exp.val(10)), 
		Exp.eq(Exp.stringBin("y"), Exp.val("abc")), 
		Exp.or(
				Exp.eq(Exp.intBin("z"), Exp.val(1)), 
				Exp.eq(Exp.intBin("z"), Exp.val(2)))
		)));


	Deserialized Json
	We can Provide below json and generate above java syntax
	
	{
	  "exps": [
		{
		  "exps": [
			{
			  "name": "x",
			  "type": "INT"
			},
			{
			  "val": 10
			}
		  ],
		  "cmd": "EQ"
		},
		{
		  "exps": [
			{
			  "name": "y",
			  "type": "STRING"
			},
			{
			  "val": "abc"
			}
		  ],
		  "cmd": "EQ"
		},
		{
		  "exps": [
			{
			  "exps": [
				{
				  "name": "z",
				  "type": "INT"
				},
				{
				  "val": 1
				}
			  ],
			  "cmd": "EQ"
			},
			{
			  "exps": [
				{
				  "name": "z",
				  "type": "INT"
				},
				{
				  "val": 2
				}
			  ],
			  "cmd": "EQ"
			}
		  ],
		  "cmd": "OR"
		}
	  ],
	  "cmd": "AND"
	}

### Postman Collection
Postman collection is also shared with project

### Conclusion:
This README file provides an overview of the Aerospike Expression module and its functionality. It also includes examples of Aerospike expressions and their corresponding Java syntax and JSON.

## Micronaut 3.9.1 Documentation

- [User Guide](https://docs.micronaut.io/3.9.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.9.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.9.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)
