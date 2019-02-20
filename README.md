# Simple Telematics Processor

The processor should be stable, graceful in the face of relatively high volume, and error resistant given a variety of suboptimal inputs.

  - Functionality (particularly that most likely to change) is compartmentalized and modular, and can generally be modified with only minor upstream and downstream changes to support interface changes
  - Like pieces of functionality are grouped in a sensical manner to facilitate ease of maintenance
  - All models are built to be extensible and are accessed in such a way that they can be extended without breaking (or even necessarily changing) existing functionality

# Design methodology

  - The implementation is object-oriented, but not just for the sake of being so.  Models are separated from unrelated functionality which is separated from utility code, and each contain internal-only (private) methods that necessitate instantiation of new instances of objects as well as publicly-accessible static (and therefore accessible from a generic instance) methods where each respective exposure made the most sense.
  - There was no compelling need for interfaces or inheritance, so you won't find any implemented.  "Best practices" are only truly so when there's a legitimate reason for them, and small projects often suffer from over-complication in the name of pedantic properness.
  - I wrote the code as if I were going to be the person to maintain it.  While it's far from unoptimized, I opted in many places for simpler-to-read implementations of logic that could have been minutely faster or sleeker at the expense of legibility and understandability.  Maintaining code costs money, just like CPU cycles do, and devs are more expensive than hardware.

# The nuts and bolts

  - I made a conscious decision to physically associate drivers and trips, as they are logically associated and my custom implementations of comparation code to fit the Driver class as well as Java 8's Stream API removed the necessity of using a HashMap or similar to swiftly identify Drivers as the key for trips and associate them without iterating through the list.
  - The main class consists simply of two call-outs and exception handling for missing and malformed files.  I buit a second version in a branch with a fairly robust user interface that continually prompted the user to provide the right file until it existed, but I omitted it in the final revision as it wasn't a requirement.
  - The main components are:
```
Data and Domain Modeling
File Processing
Trip and Driver Processing and Assignment
Telematics Calculations
Report Generation
```
  - Each is distinctly separate from the rest except for the Domain Modeling and the Telematics Calculations, which were intentionally grouped for portability and modularity.  They could easily be separated, but at the expense of additional complication each time they were implemented.  Keeping them together ensures that the calculations and the underlying data are "married," if you will.

# The starting point
The following utility call (an example of a public, static implementation of generic functionality) reads the file:
```java
fileLines = FileHandler.readTelemetryFile(basePath + args[0]);
```
And this one sends the data off to process and generate the report.  The name is so specific because there are two of them, per my previous email.
```java
ReportGenerator.generateSimpleDriverReportUsingTotalAverages(DriverTripsMapper.mapFileToDriversList(fileLines));
```
And that's all there is to it!  Two lines to sit on for debugging, and you can walk right into the rest of the code to troubleshoot.

There are also specific user error messages for:
  - No arguments passed
  - Specified file not found
  - Generic IO exceptions not caught and handled elsewhere

```ps
> java -jar .\SimpleTelematics-1.0.0-jar-with-dependencies.jar
You must pass an argument indicating the name of the target file!
Example: java -jar jarName targetFile.dat

> java -jar .\SimpleTelematics-1.0.0-jar-with-dependencies.jar badFile.dat
File specified was not found!
Please ensure the file is in the same directory as the JAR and that its name is correct!

> java -jar .\SimpleTelematics-1.0.0-jar-with-dependencies.jar rawTrips.dat
Alex: 42 miles @ 34 mph
Dan: 39 miles @ 47 mph
Bob: 0 miles
```

# The models
### Driver 
Driver directly owns a list of trips, as the same is logically true.  Driver can be easily extended to contain more and more driver data without compromising trip processing.  
```java
public class Driver {
	private String driverName;
	private List<Trip> driverTrips;
```
If more fields clearly IDENTIFY driver, they can be added to the custom "equals" implementation seamlessly.
```java
@Override
	public boolean equals(Object object) {
		if (object != null && object instanceof Driver) {
			return ((Driver) object).getDriverName().equalsIgnoreCase(this.getDriverName());
		} else {
			return false;
		}
	}
```
Driver also contains internal functionality to allow it to directly process its own trip data in a modular fashion so it can be universally used across implementations.

You're able to add a trip without worrying about the current state of the list with:
```java
public void addDriverTrip(Trip driverTrip)
```

You're also able to calculate driver-level totals based on the current trip list internally, for extra modularity:
```java
public int getTotalMilesForAllTrips()
public int getTotalMinutesForAllTrips()
```
These two methods are of note because they are logically the same but functionally different.
  - The first internally uses each trip's pre-calculated average speed divided by the number of trips to determine driver-average speed
  - The second internally uses the raw total of ALL time driven in conjunction with the raw total of ALL mileage driven to determine the driver-average speed.
```java
public int getAverageSpeedForAllTrips()
public int getAverageSpeedForAllTripsBasedOnTotals()
```

### Trip
Trip is a simple construct that houses all pieces of trip telemetry from the file and, again, can be easily extended with minimal outside impacts

I leveraged a few new pieces of Java 8 functionality for this project, but some of my favorites probably revolved around the new ```java.time``` API that completely removed the need for external libraries like JodaTime, and replaced many cumbersome ```java.util.date``` logical implementations.  LocalTime is a clean, simple way to represent a raw time in hours, minutes, and seconds without playing the notorious "everything is a calendar!!" game.

Casts are clean and relatively safe, there's a correlated ```Duration``` implementation that takes the guesswork out of figuring out intervals, and the amount of code you need to write is cut into fractions compared to Java 7 and prior.
```java
public class Trip {
	private LocalTime startTime;
	private LocalTime endTime;
	private Double milesDriven;
	private int averageSpeed;
```

Things like average speed are automatically calculated upon object initialization, so the calling and consuming code do not need to be even remotely cognizant of how they work.
```java
	public Trip(LocalTime start, LocalTime end, double miles) {
		this.startTime = start;
		this.endTime = end;
		this.milesDriven = miles;
		this.averageSpeed = calculateAverageSpeed();
	}
	
	private int calculateAverageSpeed() {
		return (int) Math.round(milesDriven / ((double) Duration.between(startTime, endTime).getSeconds()) * 3600);
	}
```
Trip also contains a method I created long after its original design both to demonstrate its modular extensibility and to feed the new reporting method I later became aware of.  Including this method inside the model itself means it can be implemented anywhere the model is present rather than being constrained to both a manual implementation AND the presence of the data in the model.
```java
	public int calculateTripMinutes() {
		return (int) (((double) Duration.between(startTime, endTime).getSeconds()) / 60);
	}
```

# IO Processing
### File processor
The file processor is simple and therefore fairly error-resistant.  The only true logic implemented inside of it is "don't add blank or spaces-only lines to the list," and errors are thrown to the caller where they can be more cleanly handled and presented to the user.

The logic to check for empty and spaces-only lines is implemented with String, which is a potential memory drain, but was done intentionally for the sake of readability.  In my more full-featured branch, I implemented StringBuffer (which can be zeroed out to clear memory) and custom "trimming" at the byte-array level to be more efficient, but was a monstrosity to read and therefore maintain.
```java
		// Read each line of the space-delimited file into a list
		while (sc.hasNextLine()) {
			// Relatively inefficient, but cleanest way to ignore empty lines in the file
			if(!(line = sc.nextLine()).trim().isEmpty()) {
				fileLines.add(Arrays.asList(line.split(" ")));
			}
		}
```
### Report generation
The report generator has two reports implemented currently, per my emailed questions.

The one I originally designed exclusively leverages the purpose-built Driver-level calculatory methods which keeps it very clean and readable.
```java
	public static void generateSimpleDriverReport(List<Driver> drivers) {
		drivers.sort(new SortByMileage());

		for (Driver driver : drivers) {
			System.out.print(driver.getDriverName() + ": " + driver.getTotalMilesForAllTrips() + " miles");
			if (driver.getTotalMilesForAllTrips() > 0) {
				System.out.print(" @ " + driver.getAverageSpeedForAllTrips() + " mph");
			}
			System.out.print(System.lineSeparator());
		}
	}
```
The one that's currently implemented uses a different method to get te average speed, and the difference is explained above in the Driver object section.
```java
	public static void generateSimpleDriverReportUsingTotalAverages(List<Driver> drivers) {
		drivers.sort(new SortByMileage());

		for (Driver driver : drivers) {
			System.out.print(driver.getDriverName() + ": " + driver.getTotalMilesForAllTrips() + " miles");
			if (driver.getTotalMilesForAllTrips() > 0) {
				System.out.print(" @ " + driver.getAverageSpeedForAllTripsBasedOnTotals() + " mph");
			}
			System.out.print(System.lineSeparator());
		}
	}
```
# The mapper
Arguably one of the most important parts of the process (aside from the actual math) is ensuring that the right trips get assigned to the right driver.  The class simply consumes an undefined list of "file lines" and then uses positional logic to process.  This makes it incredibly easy to add attributes to either driver or trip rows and enable their functionality simply by extending the processor shown below.

### Driver
Processing the Driver command is simple.  The very first item in the record is the command, and the second is the driver name.  Adding driver last name, VIN, etc, would simply be a matter of adding those fields to the Driver model, adding them to the constructor, and mapping them here.
```java
				if (fileLine.get(0).equalsIgnoreCase("driver")) {
					Driver newEntry = new Driver(fileLine.get(1));
					if (!driverData.contains(newEntry)) {
						driverData.add(newEntry);
					}
				}
```

### Trips
The Trip processor is a touch dicier, but not overly so.

Upon identifying a "trip" record and creating a new Trip model from it (which automatically calculates the average speed, remember), there is a logic block to discard "improbable" trips and then a Java Stream implementation to find the correlated driver (wherever in the list it may reside) and assign the trip to it.  This is significantly more efficient AND readable than iterating through the entire list looking for the right one for each trip.

If Driver were extended to include last name or VIN or a dozen other fields, the only changes required to continue matching would be to Driver's own internal overridden implementation of the ```.equals()``` method.
```java
				else if (fileLine.get(0).equalsIgnoreCase("trip")) {
					Driver newEntry = new Driver(fileLine.get(1));
					Trip thisTrip = new Trip(LocalTime.parse(fileLine.get(2)), LocalTime.parse(fileLine.get(3)),
							Double.parseDouble(fileLine.get(4)));
					if ((5 < thisTrip.getAverageSpeed() && thisTrip.getAverageSpeed() < 100)) {
						driverData.stream().filter(driver -> newEntry.equals(driver)).findFirst().get()
								.addDriverTrip(thisTrip);
					}
				}
```

This is another place that I originally added, then later stripped out, additional functionality that was not explicitly required by the design instructions.
  - In the event that a trip event is present before a driver is registered, the trip will currently be output as an error message stating that the driver was not found.
  - I previously implemented and then removed (given the explicit description of each operation's intent and unknown business drivers behind that specificity) functionality to first check if a driver existed and, if not, register that driver entity and associate the trip with them.  
  - The potential downside to such functionality would be that if you DID extend driver with additional data, that data would not be present in a "partially-registered" driver generated by the presence of a trip until a proper registration record came through for them.
  - The upside is that you wouldn't be "losing" good trips for unregistered drivers, they would simple be attached to an incomplete profile until registration was completed at a later point in time.

#### Exceptions are handled in three distinct ways:
  - "Driver not found" exceptions caused by a trip appearing before a registration in the file have specific verbiage, driven by the type of exception thrown
  - General "bad data" and other exceptions have more generic verbiage, with the record in question and the exception message both displayed.
  - Records that do not begin with the Driver or Trip commands are simply ignored
```sh
Exception occurred processing line: [Trip, MrDontTouchMe, 08:00, 09:00, 60.0]
There was no registered user to assign the trip!

Exception occurred processing line: [Trip, John, 1212, 13:12, 60]
Exception: Text '1212' could not be parsed at index 2
```

# Testing
My unit test approach was thorough, but not "every permutation of every possible combination of everything" thorough.  I took a "change-based testing" approach in which I tested combinations that would produce potentially different results, but did NOT retest the same logic again and again with different (but functionally identical) values just to make sure everything would behave the same.

Logically, we can be reasonably certian that we understand our code and how it behaves given different data points that trigger distinct logic blocks, and should test around LOGICAL changes rather than data changes that do not invoke different logic.

The file I used for my unit tests included:
  - Blank records
  - Spaces-only records
  - Trips that preceded their correlated Driver registration commands
  - Implausible data (both < 5mph and > 100mph average trip speed)
  - Nonsense rows that did not begin with a command
  - Valid trip records with malformed data inside of them

A sample can be found below, and also in ```/src/test/resources/``` which contains the copy used by the unit tests themselves.

```

Trip MrDontTouchMe 08:00 09:00 60.0
Driver Dan
Driver Alex
 
Driver Bob
Driver MrDontTouchMe
Trip MrDontTouchMe 08:00 09:00 60.0
Trip Dan 07:15 07:45 17.3
Trip Dan 06:12 06:32 21.8
Trip Alex 12:01 13:16 42.0
Trip MrDontTouchMe 09:00 12:00 180.0
Trip MrDontTouchMe 09:00 09:30 180.0
Trip MrDontTouchMe 09:00 19:00 10.0

Trip John 1212 13:12 60
Nonsense John moreNonsense Data12

```

# So long and thanks for all the fish?
I hope you enjoyed (or at least don't find me an idiot after) reading the walkthrough!

It was refreshing to be able to take a small, simple problem and actually be able to throw some proper problem solving after it instead of just kludging in the fastest answer to play nicely with existing interfaces and timelines, to be honest.  Regardless of whether or not I'm the caliber of developer you guys are looking to have head up your team, it was a pleasure to be able to bust out my developer hat again and actually throw some clean code!

Thanks again for your time, and please don't hesitate to reach out if you'd like to discuss the solution or ask any questions!

# Source on [GitHub]


[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)

   [GitHub]: <https://github.com/tjcongrove1/SimpleTelematics>
