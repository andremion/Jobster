<img alt="Icon" src="https://github.com/andremion/Jobster/assets/12762356/c841549c-1bd6-4640-9cc4-24bd42711ead" width=100 align="left" hspace="1" vspace="1">

# Jobster

A proof of concept of [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) targeting Android and iOS.

The app helps you discover and access content links from job postings of your interest using [Google Gemini API](https://ai.google.dev/).

## Preview

Android|iOS
-|-
<video src="https://github-production-user-asset-6210df.s3.amazonaws.com/12762356/297455947-b673c7fb-7601-4f06-b960-8822db89ac37.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240117%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240117T165737Z&X-Amz-Expires=300&X-Amz-Signature=61b0a3d54fe62d77143b5b9f528442e4d6081aff9aa464b745e8c8a1ab30ba2b&X-Amz-SignedHeaders=host&actor_id=12762356&key_id=0&repo_id=738156317" autoplay />|<video src="https://github-production-user-asset-6210df.s3.amazonaws.com/12762356/297455981-746185d4-0e50-412d-9051-d83cd5afa729.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240117%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240117T165904Z&X-Amz-Expires=300&X-Amz-Signature=cbf6fa23ac98a3fcb27dabc92a1fad4a7e1bdc5d33a11e409c8c9ced4744e979&X-Amz-SignedHeaders=host&actor_id=12762356&key_id=0&repo_id=738156317" autoplay />

The app was almost 100% built using multiplatform libraries, including the UI.

## Gemini API

This is the only one that is NOT a Multiplatform Kotlin library.
There are two implementations, one for [Android](shared/data/src/androidMain/kotlin/io/github/andremion/jobster/data/remote/api/GeminiApiImpl.kt) and one for [iOS](iosApp/iosApp/data/GeminiApiImpl.swift).

### Available regions

The Gemini API is currently available in [180+ countries](https://ai.google.dev/available_regions#available_regions), check out the documentation to learn more.

## TODO
- Make the text strings multiplatform resources.

## Other multiplatform libraries used

- [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform): A declarative framework based on Jetpack Compose and developed by JetBrains and open-source contributors for sharing UIs across multiple platforms with Kotlin.
- [PreCompose](https://github.com/Tlaster/PreCompose): Supports navigation and view models providing similar APIs to Jetpack ones.
- [Compottie](https://github.com/alexzhirkevich/compottie): A port of [Lottie Compose](https://github.com/airbnb/lottie/blob/master/android-compose.md).
- [SQLdelight](https://github.com/cashapp/sqldelight): Generates typesafe Kotlin APIs from SQL statements.
- [Koin](https://github.com/InsertKoinIO/koin): A pragmatic lightweight dependency injection framework.
- [Ktor Client](https://github.com/ktorio/ktor): A library for fetching data from the internet. Written in Kotlin from the ground up.
- [Ksoup](https://github.com/MohamedRejeb/Ksoup): A multiplatform library for parsing HTML and XML. It's a port of the renowned Java library, [jsoup](https://jsoup.org/).

## Credits

Icon by <a href="https://freeicons.io/profile/489957">Sorembadesignz</a> on <a href="https://freeicons.io">freeicons.io</a>

## MIT License

    Copyright (c) [2024] André Luiz Oliveira Rêgo
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
