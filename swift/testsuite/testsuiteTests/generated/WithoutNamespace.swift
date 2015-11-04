import Foundation
import SwiftyJSON

struct WithoutNamespace: JSONSerializable {
    
    let field1: String?
    
    init(
        field1: String?
    ) {
        self.field1 = field1
    }
    
    static func read(json: JSON) -> WithoutNamespace {
        return WithoutNamespace(
            field1: json["field1"].string
        )
    }
    func write() -> JSON {
        var json: [String : JSON] = [:]
        if let field1 = self.field1 {
            json["field1"] = JSON(field1)
        }
        return JSON(json)
    }
}