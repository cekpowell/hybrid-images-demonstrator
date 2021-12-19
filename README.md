# Hybrid Images Demonstrator

---

<video alt="Demonstration" src="https://user-images.githubusercontent.com/60888912/146680768-9e17719f-696b-41ab-bf5e-26995a8f608c.mov" width="550"></video>

---

## Contents

- **[Introduction](#introduction)**
  * **[Project Description](#project-description)**
  * **[Application Structure](#application-structure)**
- **[Running The Application](#running-the-application)**
  * **[Using the Uber-JAR File](#using-the-uber-jar-file)**
  * **[Using Maven](#using-maven)**
- **[Usage](#usage)**
  * **[Loading and Configuring Input Images](#loading-and-configuring-input-images)**
    + **[Loading Images From Files](#loading-images-from-files)**
    + **[Clearing Loaded Images](#clearing-loaded-images)**
    + **[Loading Sample Images](#loading-sample-images)**
    + **[Configuring Image Sigma Values](#configuring-image-sigma-values)**
    + **[Swapping Images and Sigma Values](#swapping-images-and-sigma-values)**
  * **[Making Hybrid Images](#making-hybrid-images)**
  * **[Saving Output Images](#saving-output-images)**
- **[Acknowledgements](#acknowledgements)**

---

## Introduction 

### Project Description

- For  [Coursework 2 of COMP3204: Computer Vision](https://github.com/cekpowell/comp3204-coursework-2), the [OpenIMAJ](http://openimaj.org/) library was used to write programs that perform [Template Convolution](http://comp3204.ecs.soton.ac.uk/cw/template_convolution_4th_edition.pdf) and generate [Hybrid Images](https://stanford.edu/class/ee367/reading/OlivaTorralb_Hybrid_Siggraph06.pdf).

- The goal of this project was to use JavaFX to create a **demonstrator application** for the Hyrbid Images generator made in this coursework.

### Application Structure

- The application can be broken down into three sections:
  - **Low Image View**: Used to input the image whose low frequencies will be used in the hybrid image, and control the parameters of this process.
  - **High Image View**: Used to input the image whose high frequencies will be used in the hybrid image, and control the parameters of this process.
  - **Output View**: Displays the low and high pass versions of the respective input images, and the resulting hybrid image made by combining these low and high pass versions.

---

## Running The Application

*A step-by-step guide on how to run the demonstrator application.*

### Using the Uber-JAR File

- Contained in the `build/` directory of the repo is the `hyrbid-images-demonstrator.jar` Uber-JAR file, which contains everything needed to run the application.
- When in the same directory as the JAR file, use the following command to run it (permissions may need to be enabled):

```bash
java -jar hybrid-images-demonstrator.jar
```

### Using Maven

- The project can be run (and built) using the provided `pom.xml` file with Maven.
- When in the home directory of the repo, use the following command to run the application:

```bash
mvn clean javafx:run
```

- And use the following command to build the application (build JAR placed in the `target/` directory):

```bash
mvn clean package
```

- On start up, the application should look like this:

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680040-cb4f433b-09c0-4d7e-be7e-fa1ba7855773.png"></p>

---

## Usage

*A short guide on the main features of the application.*

### Loading and Configuring Input Images

- Two images are required to form a hybrid image - a "low image" (low frequencies used in the hybrid image) and a "high image" (high frequencies used in the hybrid image).
- The left side of the application contains two "Image Loaders" that can be used to load and configure these images.

#### Loading Images From Files

- Each Image Loader has a toolbar, which contains a `Load` button that can be used to load an image from the local system into the application

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680121-7f24f121-f75a-4b58-9cbd-539fb0c98173.png"></p>

- When an image has been successfully loaded, a preview of the image will appear in the Image loader.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680212-f0e5b632-b3a6-451d-8502-2df19126233e.png"></p>

- It is also possible to **drag-and-drop** a file into an image loader in order to load it into the application.

#### Clearing Loaded Images

- Once an image has been loadd into a loader, the `Clear` button for this loader will be enabled, which allows for the image to be removed from the loader.

#### Loading Sample Images

- Contained in the toolbar between the two image loaders is the `Load Sample Image` button, which can be used to load images from a collection of pairs of images that are known to work well as hybrid images.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680264-35ca9378-c29f-439e-b8dd-fae4698a57bb.png"></p>

- Once selected, a window is displayed that allows for each sample image to be loaded into the application as either a low or high image.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680243-1bf9dfc0-451d-40f7-8f96-e01f050bf1be.png"></p>

#### Configuring Image Sigma Values

- Both the low and high images require the input of a **sigma value**, which determines how much of the low/high frequencies of the image are used in the resulting hybrid image.
- Each loader's toolbar has a textfield and collection of buttons that allow for the configuration of this sigma value.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680331-682ae99f-9a41-419b-bbdd-6766856d7033.png"></p>

#### Swapping Images and Sigma Values

- The toolbar between the two image loaders contains two buttons (`Swap Images` and `Swap Sigma Values`) that allow for the high and low images and sigma values to be swapped (so that the high becomes the low, and the low becomes the high). 
- Note that the `Swap Images` button is only enabled when both the low and high image loaders have images loaded into them.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680460-6eec67e5-38ce-4a0f-aa16-4f75a6215365.png"></p>

- Between the two loader images is a toolbar that contains three controls:

  - `Load Sample Images` : Allows for the loading of sample images that are known to work well as hybrid images.

  - `Swap Images`: Swaps the images within the two image loaders so that the high image becomes the low image, and the low image becomes the high image (useful in testing).

  - `Swap Sigma Values`: Swaps the sigma values within the two image loaders, so that the high sigma value becomes the low sigma value, and the low sigma value becomes the high sigma value (useful in testing).

### Making Hybrid Images

- Once both image loaders have images loaded into them, the `Make Hybrid` button will become enabled, and can be used to form a hybrid image of the low and high images.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680564-05b653ac-6ca7-470b-82e5-dca2c4b773c7.png"></p>

- When pressed, the hybrid image will be generated and displayed in the right hand side of the application. The low and high pass versions of the low and high images respectivley will also be displayed.

<video alt="Make Hybrid Process" src="https://user-images.githubusercontent.com/60888912/146680619-6485c455-b7ad-4e65-a905-5110908470af.mov" width="550"></video>

- When the hybrid image has been generated, the slider will become enabled, which can be used to change its size (useful for demonstrating the effect of the hybrid image).

<video alt="Hybrid Image Slider" src="https://user-images.githubusercontent.com/60888912/146680659-a4a9872d-6415-4074-9ccb-12bc58edc678.mov" width="550"></video>

### Saving Output Images

- When a hybrid image has been demonstrated, the `Save` buttons under the hybrid image and the low and high pass images can be used to save each image to the local system.

<p align="center"><img width="650" alt="New File Created" src="https://user-images.githubusercontent.com/60888912/146680710-6fb7312e-ec5a-4c85-b567-be70865fc0cb.png"></p>

---

## Acknowledgements

*A description of the third-party packages/programs that were used in this project.*

- [OpenIMAJ](http://openimaj.org/) : The OpenIMAJ library was used within the programs that generate the hybrid images.

---
