# Drill-Bank
A project to help me practice for coding project interviews

# Requirements

## Level 1:

Create a bank, implementing 2 main features. Account Creation and Adding Money 
to the account. Expected time 10-15 min, 15-20 lines of code

Examples:
* Account Creation
```
Ex 1: 
[CREATE, 1, account1] -> true
[CREATE, 2, account2] -> true

returns: [true, true]

Ex 2:
[CREATE, 1, account1] -> true
[CREATE, 2, account1] -> empty string

returns: ['true', '']

Ex 3:
[CREATE, 1, ''] -> empty string

returns ['']
```
* Account Add
```
Ex 1:
Account Add: Basic case 
[CREATE, 1, account1] -> true
[CREATE, 2, account2] -> true
[ADD, 3, account2, 400] -> 400
[ADD, 4, account1, 500] -> 500
[ADD, 5, account1, 500] -> 1000

returns [true, true, 400, 1000]

Ex2:
Account Add: Add to invalid account
[CREATE, 1, account1] -> true
[ADD, 2, account2, 400] -> ""

returns [true, '']
```

## Level 2: 

Implement 2 new feaures: Account Transfers, Reporting.  
Expected time 20-30 mins
30-45 lines of code

* Account Transfer
```
Ex 1:
[CREATE, 1, account1] -> true
[CREATE, 2, account2] -> true
[ADD, 3, account2, 100] -> 100
[ADD, 4, account1, 200] -> 200
[TRANSFER, 5, account1, account2, 50]

returns [true, true, 100, 200, 50]

Ex 2:
[CREATE, 1, account1] -> true
[ADD, 2, account1, 200] -> 200
[TRANSFER, 3, account1, account1, 50]

returns [true, 200, ""]
```

* Account Report
```
Ex 1:
[CREATE, 1, account1] -> true
[CREATE, 2, account2] -> true
[ADD, 3, account2, 100] -> 100
[ADD, 4, account1, 200] -> 200
[TRANSFER, 5, account1, account2, 50]

returns [true, true, 100, 200, 50]

Ex 2:
[CREATE, 1, account1] -> true
[ADD, 2, account1, 200] -> 200
[TRANSFER, 3, account1, account1, 50]

returns [true, 200, ""]
```



