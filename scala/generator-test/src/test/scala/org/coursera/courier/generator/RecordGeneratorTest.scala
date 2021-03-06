/*
 Copyright 2015 Coursera Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package org.coursera.courier.generator

import org.coursera.courier.data.IntArray
import org.coursera.courier.data.IntMap
import org.coursera.courier.templates.DataTemplates
import DataTemplates.DataConversion
import org.coursera.courier.generator.customtypes.CustomInt
import org.coursera.enums.Fruits
import org.coursera.records.test.Empty
import org.coursera.records.test.Empty2
import org.coursera.records.test.InlineOptionalRecord
import org.coursera.records.test.InlineRecord
import org.coursera.records.test.Simple
import org.coursera.records.test.SimpleMap
import org.coursera.records.test.WithComplexTypeDefaults
import org.coursera.records.test.WithComplexTypes
import org.coursera.records.test.WithInclude
import org.coursera.records.test.WithInlineRecord
import org.coursera.records.test.WithOptionalComplexTypeDefaults
import org.coursera.records.test.WithOptionalComplexTypesDefaultNone
import org.coursera.records.test.WithOptionalPrimitiveCustomTypes
import org.coursera.records.test.WithOptionalPrimitiveDefaultNone
import org.coursera.records.test.WithOptionalPrimitiveDefaults
import org.coursera.records.test.WithOptionalPrimitiveTyperefs
import org.coursera.records.test.WithOptionalPrimitives
import org.coursera.records.test.WithPrimitiveCustomTypes
import org.coursera.records.test.WithPrimitiveDefaults
import org.coursera.records.test.WithPrimitiveTyperefs
import org.coursera.records.test.WithPrimitives
import org.junit.Test

class RecordGeneratorTest extends GeneratorTest with SchemaFixtures {


  private val primitiveRecordFieldsJson = load("WithPrimitives_flex_defaults.json")

  @Test
  def testWithPrimitives(): Unit = {
    val original = WithPrimitives(1, 3000000000L, 3.3f, 4.4e38d, true, "str", bytes1)
    val roundTripped = WithPrimitives.build(roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithPrimitives(int, long, float, double, boolean, string, b) = original
    val reconstructed = WithPrimitives(int, long, float, double, boolean, string, b)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === 1)
      assert(primitives.longField === 3000000000L)
      assert(primitives.floatField === 3.3f)
      assert(primitives.doubleField === 4.4e38d)
      assert(primitives.booleanField === true)
      assert(primitives.stringField === "str")
      assert(primitives.bytesField === bytes1)

      assertJson(primitives, primitiveRecordFieldsJson)
    }
  }

  @Test
  def testWithOptionalPrimitives_Some(): Unit = {
    val original = WithOptionalPrimitives(
      Some(1), Some(3000000000L), Some(3.3f), Some(4.4e38d), Some(true), Some("str"), Some(bytes1))
    val roundTripped = WithOptionalPrimitives.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithOptionalPrimitives(int, long, float, double, boolean, string, b) = original
    val reconstructed = WithOptionalPrimitives(int, long, float, double, boolean, string, b)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === Some(1))
      assert(primitives.longField === Some(3000000000L))
      assert(primitives.floatField === Some(3.3f))
      assert(primitives.doubleField === Some(4.4e38d))
      assert(primitives.booleanField === Some(true))
      assert(primitives.stringField === Some("str"))
      assert(primitives.bytesField.get === bytes1)

      assertJson(primitives, primitiveRecordFieldsJson)

      val copy = primitives.copy(longField = None)
      assert(copy.intField === Some(1))
      assert(copy.longField === None)
      assert(copy.bytesField.get === bytes1)
    }
  }

  @Test
  def testWithOptionalPrimitives_None(): Unit = {
    val original = WithOptionalPrimitives(None, None, None, None, None, None, None)
    val roundTripped = WithOptionalPrimitives.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithOptionalPrimitives(int, long, float, double, boolean, string, b) = original
    val reconstructed = WithOptionalPrimitives(int, long, float, double, boolean, string, b)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === None)
      assert(primitives.longField === None)
      assert(primitives.floatField === None)
      assert(primitives.doubleField === None)
      assert(primitives.booleanField === None)
      assert(primitives.stringField === None)
      assert(primitives.bytesField === None)

      assertJson(primitives, "{ }")

      val copy = primitives.copy(longField = Some(3000000000L))
      assert(copy.intField === None)
      assert(copy.longField === Some(3000000000L))
      assert(copy.bytesField === None)
    }
  }

  @Test
  def testWithPrimitiveTyperefs(): Unit = {
    val original = WithPrimitiveTyperefs(1, 3000000000L, 3.3f, 4.4e38d, true, "str", bytes1)
    val roundTripped = WithPrimitiveTyperefs.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithPrimitiveTyperefs(int, long, float, double, boolean, string, b) = original
    val reconstructed = WithPrimitiveTyperefs(int, long, float, double, boolean, string, b)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === 1)
      assert(primitives.longField === 3000000000L)
      assert(primitives.floatField === 3.3f)
      assert(primitives.doubleField === 4.4e38d)
      assert(primitives.booleanField === true)
      assert(primitives.stringField === "str")
      assert(primitives.bytesField === bytes1)

      assertJson(primitives, primitiveRecordFieldsJson)
    }
  }

  @Test
  def testWithOptionalPrimitiveTyperefs_Some(): Unit = {
    val original = WithOptionalPrimitiveTyperefs(
      Some(1), Some(3000000000L), Some(3.3f), Some(4.4e38d), Some(true), Some("str"),
      Some(bytes1))
    val roundTripped = WithOptionalPrimitiveTyperefs.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithOptionalPrimitiveTyperefs(int, long, float, double, boolean, string, b) = original
    val reconstructed = WithOptionalPrimitiveTyperefs(int, long, float, double, boolean, string, b)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === Some(1))
      assert(primitives.longField === Some(3000000000L))
      assert(primitives.floatField === Some(3.3f))
      assert(primitives.doubleField === Some(4.4e38d))
      assert(primitives.booleanField === Some(true))
      assert(primitives.stringField === Some("str"))
      assert(primitives.bytesField.get === bytes1)

      assertJson(primitives, primitiveRecordFieldsJson)

      val copy = primitives.copy(longField = None)
      assert(copy.intField === Some(1))
      assert(copy.longField === None)
      assert(copy.bytesField.get === bytes1)
    }
  }

  @Test
  def testWithOptionalPrimitiveTyperefs_None(): Unit = {
    val original = WithOptionalPrimitiveTyperefs(None, None, None, None, None, None, None)
    val roundTripped = WithOptionalPrimitiveTyperefs.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithOptionalPrimitiveTyperefs(int, long, float, double, boolean, string, b) = original
    val reconstructed = WithOptionalPrimitiveTyperefs(int, long, float, double, boolean, string, b)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === None)
      assert(primitives.longField === None)
      assert(primitives.floatField === None)
      assert(primitives.doubleField === None)
      assert(primitives.booleanField === None)
      assert(primitives.stringField === None)
      assert(primitives.bytesField === None)

      assertJson(primitives, "{ }")

      val copy = primitives.copy(longField = Some(1))
      assert(copy.intField === None)
      assert(copy.longField === Some(1))
    }
  }

  @Test
  def testWithPrimitiveCustomTypes(): Unit = {
    val original = WithPrimitiveCustomTypes(CustomInt(1))
    val roundTripped = WithPrimitiveCustomTypes.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithPrimitiveCustomTypes(customInt) = original
    val reconstructed = WithPrimitiveCustomTypes(customInt)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField.value === 1)

      assertJson(primitives, load("WithPrimitiveCustomTypes.json"))

      val copy = primitives.copy(intField = CustomInt(2))
      assert(copy.intField.value === 2)
    }
  }

  @Test
  def testWithOptionalPrimitiveCustomTypes(): Unit = {
    val original = WithOptionalPrimitiveCustomTypes(Some(CustomInt(1)))
    val roundTripped = WithOptionalPrimitiveCustomTypes.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithOptionalPrimitiveCustomTypes(Some(customInt)) = original
    val reconstructed = WithOptionalPrimitiveCustomTypes(Some(customInt))

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { primitives =>
      assert(primitives.intField === Some(CustomInt(1)))

      val copy = primitives.copy(intField = None)
      assert(copy.intField === None)
    }
  }

  @Test
  def testWithInclude(): Unit = {
    val original = WithInclude(Some("message"), 1)
    val roundTripped = WithInclude.build(roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithInclude(Some(message), int) = original
    val reconstructed = WithInclude(Some(message), int)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { record =>
      assert(record.message.get === "message")
      assert(record.direct === 1)

      val copy = record.copy(message = Some("copy"))
      assert(copy.message.get === "copy")
      assert(copy.direct === 1)
    }
  }

  @Test
  def testWithInlineRecord_Some(): Unit = {
    val original = WithInlineRecord(InlineRecord(1), Some(InlineOptionalRecord("str")))
    val roundTripped = WithInlineRecord.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithInlineRecord(InlineRecord(int), Some(InlineOptionalRecord(string))) = original
    val reconstructed = WithInlineRecord(InlineRecord(int), Some(InlineOptionalRecord(string)))

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { records =>
      assert(records.inline.value === 1)
      assert(records.inlineOptional.get.value === "str")

      val copy = records.copy(inlineOptional = Some(InlineOptionalRecord("copy")))
      assert(copy.inline.value === 1)
      assert(copy.inlineOptional.get.value === "copy")
    }
  }

  @Test
  def testWithInlineRecord_None(): Unit = {
    val original = WithInlineRecord(InlineRecord(1), None)
    val roundTripped = WithInlineRecord.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithInlineRecord(InlineRecord(int), None) = original
    val reconstructed = WithInlineRecord(InlineRecord(int), None)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { records =>
      assert(records.inlineOptional == None)
    }
  }

  @Test
  def testWithComplexTypes(): Unit = {
    import WithComplexTypes.Union
    val original = WithComplexTypes(
      Simple(Some("message")),
      Fruits.APPLE,
      Union.IntMember(1),
      IntArray(1),
      IntMap("a" -> 1),
      SimpleMap("a" -> Simple(Some("message"))),
      CustomInt(1))
    val roundTripped = WithComplexTypes.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithComplexTypes(simple, fruit, union, array, map, complexMap, custom) = original
    val reconstructed = WithComplexTypes(simple, fruit, union, array, map, complexMap, custom)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { records =>
      assert(records.record === Simple(Some("message")))
      assert(records.enum === Fruits.APPLE)

      val copy = records.copy(enum = Fruits.PINEAPPLE)
      assert(copy.enum === Fruits.PINEAPPLE)
    }
  }

  @Test
  def testImplicitCollectionWrappers(): Unit = {
    val original = WithComplexTypes(
      Simple(Some("message")),
      Fruits.APPLE,
      1,
      Seq(1),
      Map("a" -> 1),
      Map("a" -> Simple(Some("message"))),
      CustomInt(1))
    val roundTripped = WithComplexTypes.build(
      roundTrip(original.data()), DataConversion.SetReadOnly)
    val WithComplexTypes(simple, fruit, union, array, map, complexMap, custom) = original
    val reconstructed = WithComplexTypes(simple, fruit, union, array, map, complexMap, custom)

    assert(original === roundTripped)
    assert(original === reconstructed)

    Seq(original, roundTripped, reconstructed) foreach { records =>
      assert(records.record === Simple(Some("message")))
      assert(records.enum === Fruits.APPLE)

      val copy = records.copy(enum = Fruits.PINEAPPLE)
      assert(copy.enum === Fruits.PINEAPPLE)
    }
  }

  @Test
  def testWithPrimitiveDefaults(): Unit = {
    val withDefaults = WithPrimitiveDefaults()
    assert(withDefaults.intWithDefault === 1)
    assert(withDefaults.longWithDefault === 3000000000L)
    assert(withDefaults.floatWithDefault === 3.3f)
    assert(withDefaults.doubleWithDefault === 4.4e38d)
    assert(withDefaults.booleanWithDefault === true)
    assert(withDefaults.stringWithDefault === "DEFAULT")
  }

  @Test
  def testWithOptionalPrimitiveDefaults(): Unit = {
    val withDefaults = WithOptionalPrimitiveDefaults()
    assert(withDefaults.intWithDefault === Some(1))
    assert(withDefaults.longWithDefault === Some(3000000000L))
    assert(withDefaults.floatWithDefault === Some(3.3f))
    assert(withDefaults.doubleWithDefault === Some(4.4e38d))
    assert(withDefaults.booleanWithDefault === Some(true))
    assert(withDefaults.stringWithDefault === Some("DEFAULT"))
  }

  @Test
  def testWithOptionalPrimitiveDefaultsNone(): Unit = {
    val withDefaults = WithOptionalPrimitiveDefaultNone()
    assert(withDefaults.intWithDefault === None)
    assert(withDefaults.longWithDefault === None)
    assert(withDefaults.floatWithDefault === None)
    assert(withDefaults.doubleWithDefault === None)
    assert(withDefaults.booleanWithDefault === None)
    assert(withDefaults.stringWithDefault === None)
  }

  @Test
  def testWithComplexTypesDefaults(): Unit = {
    import WithComplexTypeDefaults.Union
    val withDefaults = WithComplexTypeDefaults()
    assert(withDefaults.record === Simple(Some("defaults!")))
    assert(withDefaults.enum === Fruits.APPLE)
    assert(withDefaults.union === Union.IntMember(1))
    assert(withDefaults.array === IntArray(1))
    assert(withDefaults.map === IntMap("a" -> 1))
    assert(withDefaults.custom === CustomInt(1))
  }

  @Test
  def testWithOptionalComplexTypesDefaults(): Unit = {
    import WithOptionalComplexTypeDefaults.Union
    val withDefaults = WithOptionalComplexTypeDefaults()
    assert(withDefaults.record === Some(Simple(Some("defaults!"))))
    assert(withDefaults.enum === Some(Fruits.APPLE))
    assert(withDefaults.union === Some(Union.IntMember(1)))
    assert(withDefaults.array === Some(IntArray(1)))
    assert(withDefaults.map === Some(IntMap("a" -> 1)))
    assert(withDefaults.custom === Some(CustomInt(1)))
  }

  @Test
  def testWithOptionalComplexTypesDefaultNone(): Unit = {
    val withDefaults = WithOptionalComplexTypesDefaultNone()
    assert(withDefaults.record === None)
    assert(withDefaults.enum === None)
    assert(withDefaults.union === None)
    assert(withDefaults.array === None)
    assert(withDefaults.map === None)
    assert(withDefaults.custom === None)
  }

  @Test
  def testCopyDataTemplate(): Unit = {
    val original = WithOptionalComplexTypesDefaultNone(
      record = Some(Simple(
        message = Some("message"))))

    val mutableData = original.data().copy()
    mutableData.getDataMap("record").put("message", "updated message")
    val replacement = original.copy(mutableData, DataConversion.SetReadOnly)
    assert(replacement.record.get.message.get === "updated message")
  }

  @Test
  def testEquality(): Unit = {
    // Equality should work for identical records of the same type
    val record = Empty()
    val recordSame = Empty()
    assert(record == recordSame)
    assert(record.hashCode() == recordSame.hashCode())

    // Records with the same fields but different types should not be equal
    val recordSameFields = Empty2()
    assert(record != recordSameFields)
    assert(record.hashCode() != recordSameFields.hashCode())

    // Records with the same fields but different types (even if the pathless record type names
    // are the same) should not be equal.
    import org.coursera.records.test.packaging.{Empty => EmptySameName}
    val recordSameName = EmptySameName()
    assert(record != recordSameName)
    // Note that because hashCode only uses productPrefix, which is the name of the record
    // without its path, record.hashCode() will actually equal recordSameName.hashCode().
    // Fortunately, nothing should depend solely on hashCode() for determining whether two
    // records match.
  }
}
