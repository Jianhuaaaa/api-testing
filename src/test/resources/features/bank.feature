Feature: Customer bank details verification
  Verify customer bank details.

  Scenario: Valid customer bank details verification
    Given Customer bank details API
    When I prepared http post
    Then I verify valid http response
      | payment_method | bank_country_code | account_name | account_number       | swift_code | bsb    | aba       | expect_response                 |
      | LOCAL          | US                | a1           | 1                    |            |        | 184dfjeq2 | "success": "Bank details saved" |
      | LOCAL          | US                | _2ne3456#0   | 1h#8*3djie1_3~3d.    |            |        | aba@e138$ | "success": "Bank details saved" |
      | LOCAL          | AU                | d38b25       | a4m3m2               |            | 1e2dv$ |           | "success": "Bank details saved" |
      | LOCAL          | AU                | S20          | 2b(&1$/-0a           |            | des7-2 |           | "success": "Bank details saved" |
      | LOCAL          | CN                | a1b2c3d4e5   | 2dec&^%1             | ICBKCNBJ   |        |           | "success": "Bank details saved" |
      | LOCAL          | CN                | Java8080     | 1234567890abcedjiuhe |            |        |           | "success": "Bank details saved" |
      | SWIFT          | US                | a1           | 1                    | ICBKUSWT   |        | 184dfjeq2 | "success": "Bank details saved" |
      | SWIFT          | US                | _2ne3456#0   | 1h#8*3djie1_3~3d.    | ICBKUSWT   |        | aba@e138$ | "success": "Bank details saved" |
      | SWIFT          | AU                | d38b25       | a4m3m2               | ICBKAUSD   | 1e2dv$ |           | "success": "Bank details saved" |
      | SWIFT          | AU                | S20          | 2b(&1$/-0a           | ICBKAUSD   | des7-2 |           | "success": "Bank details saved" |
      | SWIFT          | CN                | a1b2c3d4e5   | 2dec&^%1             | ICBKCNBJ   |        |           | "success": "Bank details saved" |
      | SWIFT          | CN                | Java8080     | 1234567890abcedjiuhe | ICBKCNBJ   |        |           | "success": "Bank details saved" |

  Scenario: Invalid customer bank details verification
    Given Customer bank details API
    When I prepared http post
    Then I verify invalid http response
      | payment_method | bank_country_code | account name | account number                    | swift_code       | bsb                   | aba       | expect_response                                                                               |
      |                | AU                | d38b25       | a4m3m2                            |                  |                       |           | "error": "payment_method is required"                                                         |
      | LOCAL          |                   | d38b25       | a4m3m2                            |                  |                       |           | "error": "bank_country_code is required"                                                      |
      | SWIFT          | CN                |              | a4m3m2                            | ICBKCNBJ         |                       |           | "error": "account_name is required"                                                           |
      | LOCAL          | US                | d38b25       |                                   |                  |                       |           | "error": "account_number is required"                                                         |
      | LOCAL          | US                | a1           |                                   |                  |                       | 184dfjeq2 | "error": "account_number is required"                                                         |
      | LOCAL          | US                | _2ne3456#0   | abcdefghijklmnopqrst123456789#%^_ |                  |                       | aba@e138$ | "error": "Length of account_number should be between 7 and 11 when bank_country_code is 'US'" |
      | SWIFT          | AU                | a1           |                                   | ICBKAUSD         |                       | 184dfjeq2 | "error": "account_number is required"                                                         |
      | SWIFT          | AU                | _2ne3456#0   | 123ab                             | ICBKAUSD         |                       | aba@e138$ | "error": "Length of account_number should be between 6 and 9 when bank_country_code is 'AU'"  |
      | LOCAL          | CN                | a1           |                                   |                  |                       | 184dfjeq2 | "error": "account_number is required"                                                         |
      | SWIFT          | CN                | _2ne3456#0   | abcdefghijklmnopqrst123456789#%^_ | ICBKCNBJ         |                       | aba@e138$ | "error": "Length of account_number should be between 8 and 20 when bank_country_code is 'CN'" |
      | SWIFT          | CN                | a1b2c3d4e5   | 2dec&^%1                          |                  |                       |           | "error": "swift_code is required when payment method is SWIFT"                                |
      | SWIFT          | CN                | a1b2c3d4e5   | 2dec&^%1                          | ICBKAUBJ         |                       |           | "error": "The swift code is not valid for the given bank country code CN"                     |
      | SWIFT          | AU                | a1b2c3d4e5   | 2dec&^%1                          |                  |                       |           | "error": "swift_code is required when payment method is SWIFT"                                |
      | SWIFT          | AU                | a1b2c3d4e5   | 2dec&^%1                          | ICBKUSBJ         |                       |           | "error": "The swift code is not valid for the given bank country code AU"                     |
      | SWIFT          | US                | a1b2c3d4e5   | 2dec&^%1                          |                  |                       |           | "error": "swift_code is required when payment method is SWIFT"                                |
      | SWIFT          | US                | a1b2c3d4e5   | 2dec&^%1                          | ICBKCNBJ         |                       |           | "error": "The swift code is not valid for the given bank country code US"                     |
      | SWIFT          | CN                | a1b2c3d4e5   | 2dec&^%1                          | ICBKCNBJ12345678 |                       |           | "error": "Length of 'swift_code' should be either 8 or 11"                                    |
      | SWIFT          | US                | a1b2c3d4e5   | 2dec&^%1                          | ICBKUSBJ12345678 |                       |           | "error": "Length of 'swift_code' should be either 8 or 11"                                    |
      | LOCAL          | AU                | d38b25       | a4m3m2                            |                  |                       |           | "error": "bsb is required when bank country is AU"                                            |
      | LOCAL          | AU                | S20          | 2b(&1$/-0a                        |                  | des7-2_abdkjiwfhs;hfd |           | "error": "Length of 'bsb' should be 6 characters"                                             |
      | LOCAL          | US                | d38b25       | a4m3m2                            |                  |                       |           | "error": "aba is required when bank country is US"                                            |
      | LOCAL          | US                | S20          | 2b(&1$/-0a                        |                  |                       | hi20      | "error": "Length of 'aba' should be 9 characters"                                             |
