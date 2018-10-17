# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
			 imooc-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/imooc.cpp 
			 src/main/cpp/general.cpp 
			 src/main/cpp/dynamic.cpp 
			 src/main/cpp/demo_log.cpp
			 src/main/cpp/basic/refer.cpp
			 src/main/cpp/swig/unix.cpp
			 src/main/cpp/swig/web/shape.cpp
			 src/main/cpp/swig/plus/plus.cpp
			 src/main/cpp/swig/c2j/c2j.cpp
			 src/main/cpp/ch06/memory_c.c
			 src/main/cpp/ch06/memory_c_plus.cpp
			 src/main/cpp/ch06/io.cpp
			 src/main/cpp/ch06/process.cpp
			 src/main/cpp/ch06/ch06.cpp
)
add_library( # Sets the name of the library.
			 thread-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
			 src/main/cpp/ch07/ch07.cpp
)
add_library( # Sets the name of the library.
			 socket-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
			 src/main/cpp/socket/echo.cpp
)

#add_library( # Sets the name of the library.
#			 transcode-lib

#            # Sets the library as a shared library.
#             SHARED
#
#             # Provides a relative path to your source file(s).
#			 src/main/cpp/transcode/avilib.c
#			 src/main/cpp/transcode/platform_posix.c
#)

add_library( # Sets the name of the library.
			 graphic-lib

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/transcode/avilib.c
             src/main/cpp/transcode/platform_posix.c
			 src/main/cpp/graphic/Common.cpp
			 src/main/cpp/graphic/AbstractPlayerActivity.cpp
			 src/main/cpp/graphic/BitmapPlayerActivity.cpp
			 src/main/cpp/graphic/OpenGLPlayerActivity.cpp
			 src/main/cpp/graphic/NativeWindowPlayerActivity.cpp
)

add_library(
        wav-lib

        SHARED

        src/main/cpp/transcode/wavlib.c
		src/main/cpp/wav/WavActivity.cpp
)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

find_library( # Sets the name of the path variable.
              jniGraphics-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
               jnigraphics)

find_library( # Sets the name of the path variable.
              GLESv1-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
               GLESv1_CM)

find_library( # Sets the name of the path variable.
              GLESv2-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
               GLESv2_CM)

find_library( # Sets the name of the path variable.
              android-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
               android)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       imooc-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )
					   
target_link_libraries( # Specifies the target library.
                       thread-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )

target_link_libraries( # Specifies the target library.
                       socket-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )

target_link_libraries( # Specifies the target library.
                       graphic-lib

                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib}
                       ${jniGraphics-lib}
                       ${GLESv1-lib}
                       ${android-lib} )

target_link_libraries(
        wav-lib

        ${log-lib}
        ${android-lib}
        OpenSLES
)
