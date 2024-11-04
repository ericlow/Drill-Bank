package elow.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bank Tests")
class BankTest {
    private static final String CMD_CREATE = "CREATE";
    private static final String CMD_ADD = "ADD";
    private static final String CMD_TRANSFER = "TRANSFER ACCT";
    private static final String CMD_REPORT ="TRANSFER REPORT";


    @Nested
    @DisplayName("Account Creation Tests")
    class AccountCreationTests {
        @Test
        void basicCase() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[2][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(new String[] {"true", "true"}, results);
        }

        @Test
        @DisplayName("Duplicate account name - Second creation should fail")
        void duplicateAccountName() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[2][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account1"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(new String[] {"true", ""}, results);
        }

        @Test
        @DisplayName("Invalid account name - Empty string not allowed")
        void invalidEmptyAccountName() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[1][];
            commands[0] = new String[] {CMD_CREATE, "1", ""};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(new String[] {""}, results);
        }

    }


    @Nested
    @DisplayName("Account Addition Tests")
    class AccountAdditionTests {
        @Test
        @DisplayName("Basic case - Add money to existing accounts")
        void basicAddCase() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[4][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "2", "account2", "400"};
            commands[3] = new String[] {CMD_ADD, "2", "account1", "500"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "400", "500"},
                    results,
                    "Should successfully create accounts and add money"
            );
        }

        @Test
        @DisplayName("Add to non-existent account")
        void addToInvalidAccount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[2][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_ADD, "2", "account2", "400"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", ""},
                    results,
                    "Should fail when adding to non-existent account"
            );
        }

        @Test
        @DisplayName("Add negative amount")
        void addNegativeAmount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[2][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_ADD, "2", "account1", "-400"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", ""},
                    results,
                    "Should fail when adding negative amount"
            );
        }

        @Test
        @DisplayName("Add zero amount")
        void addZeroAmount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[2][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_ADD, "2", "account1", "0"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", ""},
                    results,
                    "Should fail when adding zero amount"
            );
        }

        @Test
        @DisplayName("Add to specific accounts with different IDs")
        void addToSpecificAccounts() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[4][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account2", "100"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "200"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", "200"},
                    results,
                    "Should add amounts to specific accounts with different IDs"
            );
        }

        @Test
        @DisplayName("Add multiple values to account - Check running total")
        void addMultipleValuesToAccount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[5][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account2", "100"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "200"};
            commands[4] = new String[] {CMD_ADD, "5", "account2", "300"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", "200", "400"},
                    results,
                    "Should track running total for multiple additions to same account"
            );
        }
    }


    @Nested
    @DisplayName("Account Transfer Tests")
    class AccountTransferTests {

        @Test
        @DisplayName("Basic transfer between accounts")
        void basicTransferCase() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[5][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account2", "100"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "200"};
            commands[4] = new String[] {CMD_TRANSFER, "5", "account1", "account2", "10"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", "200", "90"},
                    results,
                    "Should set up accounts with initial balances for transfer"
            );
        }

        @Test
        @DisplayName("Transfer twice from same account")
        void transferTwice() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[6][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "500"};  // Initial balance
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "200"};  // First transfer
            commands[4] = new String[] {CMD_TRANSFER, "5", "account1", "account2", "100"};  // Second transfer
            commands[5] = new String[] {CMD_TRANSFER, "6", "account1", "account2", "150"};  // Third transfer

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "500", "300", "200", "50"},
                    results,
                    "Should allow multiple transfers and track running balances correctly"
            );
        }

        @Test
        @DisplayName("Transfer from non-existent source account")
        void transferFromNonExistentAccount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[3][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};  // Only create destination account
            commands[1] = new String[] {CMD_ADD, "2", "account1", "100"};  // Add some money to destination
            commands[2] = new String[] {CMD_TRANSFER, "3", "nonexistent", "account1", "50"};  // Try to transfer from non-existent account

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "100", ""},
                    results,
                    "Should reject transfer from non-existent account with empty string"
            );
        }

        @Test
        @DisplayName("Transfer amount exceeding balance")
        void transferExceedingBalance() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[4][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "100"};  // Initial balance 100
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "150"};  // Try to transfer 150

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", ""},
                    results,
                    "Should reject transfer when amount exceeds balance"
            );
        }

        @Test
        @DisplayName("Transfer to non-existent destination account")
        void transferToNonExistentAccount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[3][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_ADD, "2", "account1", "100"};
            commands[2] = new String[] {CMD_TRANSFER, "3", "account1", "nonexistent", "50"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "100", ""},
                    results,
                    "Should reject transfer to non-existent account"
            );
        }

        @Test
        @DisplayName("Transfer zero amount")
        void transferZeroAmount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[4][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "100"};
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "0"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", ""},
                    results,
                    "Should reject transfer of zero amount"
            );
        }

        @Test
        @DisplayName("Transfer negative amount")
        void transferNegativeAmount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[4][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "100"};
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "-50"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", ""},
                    results,
                    "Should reject transfer of negative amount"
            );
        }

        @Test
        @DisplayName("Transfer to same account")
        void transferToSameAccount() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[3][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_ADD, "2", "account1", "100"};
            commands[2] = new String[] {CMD_TRANSFER, "3", "account1", "account1", "50"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "100", ""},
                    results,
                    "Should reject transfer to same account"
            );
        }

        @Test
        @DisplayName("Multiple transfers between multiple accounts")
        void multipleTransfersBetweenAccounts() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[8][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_CREATE, "3", "account3"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "500"};
            commands[4] = new String[] {CMD_TRANSFER, "5", "account1", "account2", "200"};  // account1: 300, account2: 200
            commands[5] = new String[] {CMD_TRANSFER, "6", "account2", "account3", "100"};  // account2: 100, account3: 100
            commands[6] = new String[] {CMD_TRANSFER, "7", "account1", "account3", "100"};  // account1: 200, account3: 200
            commands[7] = new String[] {CMD_TRANSFER, "8", "account2", "account1", "50"};   // account1: 250, account2: 50

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "true", "500", "300", "100", "200", "50"},
                    results,
                    "Should correctly track balances through multiple transfers"
            );
        }

        @Test
        @DisplayName("Transfer exact balance amount")
        void transferExactBalance() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[4][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "100"};
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "100"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "100", "0"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

    }

    @Nested
    @DisplayName("Account Report Tests")
    class AccountReportTests {
        @Test
        @DisplayName("Return the median of 3 transactions")
        void createBasicReport() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[7][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "1000"};
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "100"};
            commands[4] = new String[] {CMD_TRANSFER, "5", "account1", "account2", "200"};
            commands[5] = new String[] {CMD_TRANSFER, "6", "account1", "account2", "300"};
            commands[6] = new String[] {CMD_REPORT, "7", "1"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "1000", "900", "700","400", "account1(200)"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

        @Test
        @DisplayName("return the median of 2 transactions")
        void reportTwoTransfers() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[6][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "1000"};
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "100"};
            commands[4] = new String[] {CMD_TRANSFER, "5", "account1", "account2", "200"};
            commands[5] = new String[] {CMD_REPORT, "6", "1"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "1000", "900", "700", "account1(150)"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

        @Test
        @DisplayName("return the median of 4 transactions")
        void reportEvenNumbersOfTransfers() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[8][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_ADD, "3", "account1", "2000"};
            commands[3] = new String[] {CMD_TRANSFER, "4", "account1", "account2", "100"};
            commands[4] = new String[] {CMD_TRANSFER, "5", "account1", "account2", "200"};
            commands[5] = new String[] {CMD_TRANSFER, "6", "account1", "account2", "300"};
            commands[6] = new String[] {CMD_TRANSFER, "7", "account1", "account2", "400"};
            commands[7] = new String[] {CMD_REPORT, "8", "1"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "1000", "900", "700", "account1(150)"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

        @Test
        @DisplayName("Report on all accounts")
        void reportOnAllAccounts() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[9][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_CREATE, "3", "account3"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "2000"};
            commands[3] = new String[] {CMD_ADD, "5", "account1", "2000"};
            commands[4] = new String[] {CMD_TRANSFER, "6", "account1", "account2", "100"};
            commands[5] = new String[] {CMD_TRANSFER, "7", "account1", "account2", "200"};
            commands[6] = new String[] {CMD_TRANSFER, "8", "account2", "account3", "50"};
            commands[7] = new String[] {CMD_TRANSFER, "9", "account3", "account1", "90"};
            commands[7] = new String[] {CMD_TRANSFER, "10", "account3", "account1", "110"};
            commands[8] = new String[] {CMD_REPORT, "10", "3"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "true", "2000", "2000","1900", "1700", "250", "1960","account1(150), account3(100), account2(50)"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

        @Test
        @DisplayName("Report on top 2 accounts")
        void reportOnTop2Accounts() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[9][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_CREATE, "3", "account3"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "2000"};
            commands[3] = new String[] {CMD_ADD, "5", "account1", "2000"};
            commands[4] = new String[] {CMD_TRANSFER, "6", "account1", "account2", "100"};
            commands[5] = new String[] {CMD_TRANSFER, "7", "account1", "account2", "200"};
            commands[6] = new String[] {CMD_TRANSFER, "8", "account2", "account3", "50"};
            commands[7] = new String[] {CMD_TRANSFER, "9", "account3", "account1", "90"};
            commands[7] = new String[] {CMD_TRANSFER, "10", "account3", "account1", "110"};
            commands[8] = new String[] {CMD_REPORT, "10", "2"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "true", "2000", "2000","1900", "1700", "250", "1960","account1(150), account3(100)"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

        @Test
        @DisplayName("Report on requested more accounts than available")
        void reportOnTopAccountsTooManyRequested() {
            // Arrange
            Bank bank = new Bank();
            String[][] commands = new String[9][];
            commands[0] = new String[] {CMD_CREATE, "1", "account1"};
            commands[1] = new String[] {CMD_CREATE, "2", "account2"};
            commands[2] = new String[] {CMD_CREATE, "3", "account3"};
            commands[3] = new String[] {CMD_ADD, "4", "account1", "2000"};
            commands[3] = new String[] {CMD_ADD, "5", "account1", "2000"};
            commands[4] = new String[] {CMD_TRANSFER, "6", "account1", "account2", "100"};
            commands[5] = new String[] {CMD_TRANSFER, "7", "account1", "account2", "200"};
            commands[6] = new String[] {CMD_TRANSFER, "8", "account2", "account3", "50"};
            commands[7] = new String[] {CMD_TRANSFER, "9", "account3", "account1", "90"};
            commands[7] = new String[] {CMD_TRANSFER, "10", "account3", "account1", "110"};
            commands[8] = new String[] {CMD_REPORT, "10", "4"};

            // Act
            String[] results = bank.execute(commands);

            // Assert
            assertArrayEquals(
                    new String[] {"true", "true", "true", "2000", "2000","1900", "1700", "250", "1960","account1(150), account3(100), account2(50)"},
                    results,
                    "Should allow transfer of entire balance"
            );
        }

    }
}
