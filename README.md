# Applitools Ultrafast Grid Hackathon
1. This repository was created as a participant in the [Applitools Ultrfast Grid Hackathon](https://applitools.com/cross-browser-testing-hackathon-v20-1-instructions/). It demonstrates how to run automated tests using Selenium Webdriver (Java) on various browsers and viewport using the traditional style and then how the same set of tests are executed on actual browsers and devices using Applitools Ultrafast grid feature.  
1. The Hackathon broadly consisted of three tasks that had to be implemented the Traditional way (without using an automated visual tool) and then using Applitools.  
1. The tests need to be executed on [V1](https://demo.applitools.com/gridHackathonV1.html) and [V2](https://demo.applitools.com/gridHackathonV2.html) versions of the app where V1 was considered as the correct version to capture baseline images, whereas V2 version of the app was assumed to be buggy. 

## Prerequisites
1. Ensure you have JDK 1.8 on your machine. If no, please download it from [here](https://java.com/en/download/).  

1. To run the traditional tests flawlessly, you should have Chrome, Firefox and Safari browsers on your machine.  

1. To execute these tests successfully on Chrome browser, ensure you have downloaded the compatible Chromedriver version from [here](https://chromedriver.chromium.org/downloads).  

1. To execute these tests successfully on Firefox browser, ensure you have downloaded the compatible Geckodriver version from [here](https://github.com/mozilla/geckodriver/releases).

1. It is recommended you have a IDE installed, either [Eclipse](https://www.eclipse.org/downloads/) or [IntelliJ](https://www.jetbrains.com/idea/download).  

1. Ensure **APPLITOOLS_API_KEY** is added in your path with correct value. You can navigate to this [link](https://help.applitools.com/hc/en-us/articles/360006914732-The-runner-API-key) to understand how to do it.   

### Installing
1. Clone the repository by using the command `git clone`.  
1. Import the cloned repository as an existing Maven project in your preferred IDE.  
1. Now, you will have to make some changes to run the tests on your machine. Let's look at them one by one.  
    1. Navigate to [BaseTest.java](src/test/java/hackathonapplitools/grid/common/BaseTest.java)  
        1. On line#37, set the complete path to your chromedriver in the setProperty method by replacing *"/path/to/your/chromedriver"* with the location of the chromedriver on your machine.  
        1. Similarly, on line#43, replace *"/path/to/your/geckodriver"* with the location of the geckodriver on your machine.    
    1. Navigate to [ModernTestsV1.java](src/test/java/hackathonapplitools/grid/tests/modern/ModernTestsV1.java)
        1.  On line#49, set the complete path to your chromedriver in the setProperty method by replacing *"/path/to/your/chromedriver"* with the location of the chromedriver on your machine.
    1. Similarly, navigate to [ModernTestsV2.java](src/test/java/hackathonapplitools/grid/tests/modern/ModernTestsV2.java)
        1. On line#49, set the complete path to your chromedriver in the setProperty method by replacing *"/path/to/your/chromedriver"* with the location of the chromedriver on your machine.  

### About the tests
1. Traditional tests:  
    1. All three tasks are captured in separate class files.
    1. Task 1 - To verify element visibility on various laptop browsers, tablet and phone is implemented in [CrossDeviceElementsTest](src/test/java/hackathonapplitools/grid/tests/traditionalv1/CrossDeviceElementsTest.java).  
    1. Task 2 - To verify filter results for black colour shoe is implemented in [ShoppingExperienceTest](src/test/java/hackathonapplitools/grid/tests/traditionalv1/ShoppingExperienceTest.java).  
    1. Task 3 - To verify details displayed on the product details page is implemented in [ProductDetailsTest](src/test/java/hackathonapplitools/grid/tests/traditionalv1/ProductDetailsTest.java).  
    1. All three tasks for V2 app are implemented in a similar fashion in the package [hackathonapplitools.grid.tests.traditionalv2](src/test/java/hackathonapplitools/grid/tests/traditionalv2).  
1. Modern tests:  
    1. All three tasks are implemented in one file [ModernTestsV1](src/test/java/hackathonapplitools/grid/tests/modern/ModernTestsV1.java) for V1 version.
    1. Similarly, the same three tasks are replicated in file [ModernTestsV2](src/test/java/hackathonapplitools/grid/tests/modern/ModernTestsV2.java) to run against V2 version.
        
### Running the tests
1. **To run the entire Test Suite (All Traditional and Modern tests for both versions of the app):**  
    * Right click on testng.xml -> Run As -> TestNG Suite.  
1. **To run individual files:**  
    * Right click on Class file (For e.g. [CrossDeviceElementsTest.java](src/test/java/hackathonapplitools/grid/tests/traditionalv1/CrossDeviceElementsTest.java)) -> Run As -> TestNG Test.  
1. **To run individual tests:**  
    * In the respective class file, Right click on method annotated as @Test (For e.g. elementVisibilityOnLaptop() method in [CrossDeviceElementsTest.java](src/test/java/hackathonapplitools/grid/tests/traditionalv1/CrossDeviceElementsTest.java)) -> Run As -> TestNG Test.  
    
### Authors
* Shweta Sharma - [Axelerant Technologies](https://www.axelerant.com/) 

### License
This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](https://github.com/shwetaneelsharma/expert-octo-train/blob/master/LICENSE) file for details.  