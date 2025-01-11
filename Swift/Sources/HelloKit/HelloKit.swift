import Foundation

public enum HelloKitLoader {
  public static func loadRawHelloWorldJSON() -> String? {
    guard let url = Bundle.module.url(forResource: "hello_world", withExtension: "json"),
          let data = try? Data(contentsOf: url) else {
      return nil
    }
    // Convert the raw data to a string
    return String(data: data, encoding: .utf8)
  }
}
