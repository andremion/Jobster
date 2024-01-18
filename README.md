<img alt="Icon" src="https://github.com/andremion/Jobster/assets/12762356/c841549c-1bd6-4640-9cc4-24bd42711ead" width=100 align="left" hspace="1" vspace="1">

<a target=_blank href='https://play.google.com/store/apps/details?id=io.github.andremion.jobster.android&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' height=48 align=right /></a>

# Jobster

A proof of concept of [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) targeting Android and iOS and using [Google Gemini API](https://ai.google.dev/).

</br>

<div>

<img src="https://github.com/andremion/Jobster/assets/12762356/b76e81af-3bd9-43f3-92eb-6840e28b550a" width="20%"/><img src="https://github.com/andremion/Jobster/assets/12762356/2e9b68a2-7e9e-48b7-b993-bfa736e309ea" width="20%"/><img src="https://github.com/andremion/Jobster/assets/12762356/12c223aa-1867-4497-b96a-f0c4af4d3576" width="20%"/><img src="https://github.com/andremion/Jobster/assets/12762356/731e8e46-6682-4a7d-97e8-db0a7d4edbc0" width="20%"/><img src="https://github.com/andremion/Jobster/assets/12762356/3b3d5659-7953-4825-80b4-dbb7d106e2e3" width="20%"/>

</div>

</br>

<div align=center >

### The app is almost 100% built with multiplatform code, including the UI.

Android|iOS
-|-
<video src="https://github-production-user-asset-6210df.s3.amazonaws.com/12762356/297780536-05b28b95-eb13-4395-bbff-fe819ff2468f.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240118%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240118T145839Z&X-Amz-Expires=300&X-Amz-Signature=db6239378a1920644d50fa527aec9ad5ef08ec32f05e74bbb8a070a8e9f2b7dc&X-Amz-SignedHeaders=host&actor_id=12762356&key_id=0&repo_id=738156317" autoplay />|<video src="https://github-production-user-asset-6210df.s3.amazonaws.com/12762356/297455981-746185d4-0e50-412d-9051-d83cd5afa729.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20240117%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20240117T165904Z&X-Amz-Expires=300&X-Amz-Signature=cbf6fa23ac98a3fcb27dabc92a1fad4a7e1bdc5d33a11e409c8c9ced4744e979&X-Amz-SignedHeaders=host&actor_id=12762356&key_id=0&repo_id=738156317" autoplay />

</div>

## Gemini API

This is the only one that is NOT a Multiplatform Kotlin library.
There are two implementations, one for [Android](shared/data/src/androidMain/kotlin/io/github/andremion/jobster/data/remote/api/GeminiApiImpl.kt) and one for [iOS](iosApp/iosApp/data/GeminiApiImpl.swift).

## Available regions

The Gemini API is currently available in [180+ countries](https://ai.google.dev/available_regions#available_regions).

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
