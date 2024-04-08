# Setup your IDE

### Lombok

See https://www.baeldung.com/lombok-ide

# Test, build & run app

```
# run unit tests
./gradlew unitTest

# run integration tests
./gradlew integrationTest 

# run API tests
./gradlew integrationTest 

# run all tests
./gradlew test 

# build app
./gradlew build 

# run app
./gradlew bootRun 
```

# Modifying rules for assigning reward points

You can simply edit `src/main/resources/reward-rules.properties` to add, remove or update rules for assigning reward points.

Sample content:

```
reward.rule.include=0,1

reward.rule.0.range.start=50
reward.rule.0.range.end=100
reward.rule.0.multiplier=1

reward.rule.1.range.start=100
# reward.rule.1.range.end here is optional, because if not provided it means that range is UNBOUND
reward.rule.1.multiplier=2
```

Because this affects directly business logic, jUnit integration tests have their own `reward-rules.properties` file,
so that you don't have to update jUnit code everytime business requests to update some ranges or multipliers.


# API documentation (TODO)

See src/main/asciidoc.
