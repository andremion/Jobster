// Copyright 2023 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import Foundation

enum GeminiApiKey {
  /// Fetch the API key from `GeminiInfo.plist`
  static var `default`: String {
    guard let filePath = Bundle.main.path(forResource: "GeminiInfo", ofType: "plist")
    else {
      fatalError("Couldn't find file 'GeminiInfo.plist'.")
    }
    let plist = NSDictionary(contentsOfFile: filePath)
    guard let value = plist?.object(forKey: "GeminiApiKey") as? String else {
      fatalError("Couldn't find key 'GeminiApiKey' in 'GeminiInfo.plist'.")
    }
    if value.starts(with: "_") || value.isEmpty {
      fatalError(
        "Follow the instructions at https://ai.google.dev/tutorials/setup to get an API key."
      )
    }
    return value
  }
}
