
cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} ${PROJECT_DIR}/libandroidthings)
find_package(AndroidThings REQUIRED)
include_directories(${ANDROIDTHINGS_INCLUDE_DIRS})

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library(native-lib
            SHARED
            native-lib.cpp)

add_library(androidthings
            SHARED
            IMPORTED)

target_include_directories(native-lib PRIVATE
                           ${PROJECT_DIR}/android-system-properties
                           ${ANDROID_NDK}/sources/android/native_app_glue
                           ${ANDROIDTHINGS_INCLUDE_DIRS})


add_library(native-app-glue
            STATIC
            ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c)

add_library(android-system-properties
            STATIC
            ${PROJECT_DIR}/android-system-properties/AndroidSystemProperties.cpp)

find_library(
              log-lib
              log )


set_target_properties(androidthings
                      PROPERTIES IMPORTED_LOCATION
                      ${ANDROIDTHINGS_LIBRARIES})

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
            ad  log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )


target_link_libraries(native-lib
                      android
                      log
                      native-app-glue
                      androidthings
                      android-system-properties)

