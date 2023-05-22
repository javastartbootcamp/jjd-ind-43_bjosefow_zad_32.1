package pl.javastart.streamstask;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamsTaskTest {

    private StreamsTask streamsTask;

    @BeforeEach
    public void init() {
        streamsTask = new StreamsTask();
    }

    @Nested
    public class findWomenMethodTests {

        @Test
        public void shouldReturnEmptyListWhenNotContainsWomen() {
            //given
            List<User> users = new ArrayList<>();

            users.add(new User(1L, "Patryk", 20));
            users.add(new User(2L, "Dominik", 15));
            users.add(new User(3L, "Lucjan", 25));

            //when
            Collection<User> women = streamsTask.findWomen(users);

            //then
            assertThat(women).isEmpty();
        }

        @Test
        public void shouldReturnNotEmptyListWhenContainsWomen() {
            //given
            List<User> users = new ArrayList<>();

            users.add(new User(1L, "Patryk", 20));
            users.add(new User(2L, "Dominika", 15));
            users.add(new User(3L, "Lucjan", 25));

            //when
            Collection<User> women = streamsTask.findWomen(users);

            //then
            assertThat(women).isNotEmpty();
        }

        @Test
        public void shouldReturnCorrectWomenList() {
            //given
            List<User> users = new ArrayList<>();

            User user1 = new User(1L, "Patryk", 20);
            User user2 = new User(2L, "Dominika", 15);
            User user3 = new User(3L, "Lucyna", 25);

            users.add(user1);
            users.add(user2);
            users.add(user3);

            //when
            Collection<User> women = streamsTask.findWomen(users);

            //then
            assertThat(women).contains(user2, user3);
        }

        @Test
        public void shouldReturnWholeListWhenContainsOnlyWomen(){
            //given
            List<User> users= new ArrayList<>();

            users.add(new User(1L, "Patrycja", 20));
            users.add(new User(2L, "Dominika", 15));
            users.add(new User(3L, "Lucyna", 25));

            //when
            Collection<User> women = streamsTask.findWomen(users);

            //then
            assertThat(women).isEqualTo(users);
        }
    }

    @Nested
    public class averageMenAgeMethodTests {
        @Test
        public void shouldReturnAgeOfMenWhenListContainOnlyOneMen(){
            //given
            List<User> users= new ArrayList<>();

            users.add(new User(1L, "Patrycja", 20));
            users.add(new User(2L, "Dominika", 15));
            users.add(new User(3L, "Lucjan", 25));

            //when
            Double averageMenAge = streamsTask.averageMenAge(users);

            //then
            assertThat(averageMenAge).isEqualTo(25);
        }

        @Test
        public void shouldReturnCorrectAverageForMensAge(){
            //given
            List<User> users= new ArrayList<>();

            users.add(new User(1L, "Patrycja", 20));
            users.add(new User(2L, "Fabian", 15));
            users.add(new User(3L, "Lucjan", 25));

            //when
            Double averageMenAge = streamsTask.averageMenAge(users);

            //then
            assertThat(averageMenAge).isEqualTo(20);
        }

        @Test
        public void shouldReturnZeroWhenListNotCointainMens(){
            //given
            List<User> users= new ArrayList<>();

            users.add(new User(1L, "Patrycja", 20));
            users.add(new User(2L, "Dominika", 15));
            users.add(new User(3L, "Maryla", 25));

            //when
            Double averageMenAge = streamsTask.averageMenAge(users);

            //then
            assertThat(averageMenAge).isEqualTo(0);
        }
    }

    @Nested
    public class expensesByUserIdMdethodTests {
        @Test
        public void shouldReturnMapExpensesByUserId(){
            //given
            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

            //when
            Map<Long, List<Expense>> userIdExpensesListMap = streamsTask.groupExpensesByUserId(expenses);

            //then
            assertThat(userIdExpensesListMap).containsKeys(1L, 2L);
        }

        @Test
        public void shouldReturnMapWithoutUserIdNotInExpensesList(){
            //given
            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

            //when
            Map<Long, List<Expense>> userIdExpensesListMap = streamsTask.groupExpensesByUserId(expenses);

            //then
            assertThat(userIdExpensesListMap).doesNotContainKey(3L);
        }

        @Test
        public void shouldReturnMapExpensesByUserIdWithCorrectExpenseListSize(){
            //given
            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(1L, "Ogorki", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

            //when
            Map<Long, List<Expense>> userIdExpensesListMap = streamsTask.groupExpensesByUserId(expenses);

            //then
            assertThat(userIdExpensesListMap.get(1L).size()).isEqualTo(3);
        }

        @Test
        public void shouldReturnMapExpensesByUserIdWithCorrectExpenseList(){
            //given
            List<Expense> expenses = new ArrayList<>();
            Expense expense1 = new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR);
            Expense expense2 = new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD);
            Expense expense3 = new Expense(1L, "Ogorki", new BigDecimal("15.99"), ExpenseType.FOOD);
            Expense expense4 = new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR);

            expenses.add(expense1);
            expenses.add(expense2);
            expenses.add(expense3);
            expenses.add(expense4);

            //when
            Map<Long, List<Expense>> userIdExpensesListMap = streamsTask.groupExpensesByUserId(expenses);

            //then
            assertThat(userIdExpensesListMap).containsEntry(1L, List.of(expense1, expense2, expense3));
        }
    }

    @Nested
    public class expensesByUserMethodTests {
        @Test
        public void shouldReturnMapExpensesByUser(){
            //given
            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

            List<User> users= new ArrayList<>();

            User user1 = new User(1L, "Patrycja", 20);
            User user2 = new User(2L, "Fabian", 15);
            User user3 = new User(3L, "Lucjan", 25);
            users.add(user1);
            users.add(user2);
            users.add(user3);

            //when
            Map<User, List<Expense>> userExpensesListMap = streamsTask.groupExpensesByUser(users, expenses);

            //then
            assertThat(userExpensesListMap).containsKeys(user1, user2);
        }

        @Test
        public void shouldReturnMapExpensesByUserNotContainUserWithoutExpense(){
            //given
            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

            List<User> users= new ArrayList<>();
            User user1 = new User(1L, "Patrycja", 20);
            User user2 = new User(2L, "Fabian", 15);
            User user3 = new User(3L, "Lucjan", 25);

            users.add(user1);
            users.add(user2);
            users.add(user3);

            //when
            Map<User, List<Expense>> userExpensesListMap = streamsTask.groupExpensesByUser(users, expenses);

            //then
            assertThat(userExpensesListMap).doesNotContainKey(user3);
        }

        @Test
        public void shouldReturnMapExpensesByUserWithCorrectExpenseList(){
            //given
            List<Expense> expenses = new ArrayList<>();
            Expense expense1 = new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR);
            Expense expense2 = new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD);
            Expense expense3 = new Expense(1L, "Ogorki", new BigDecimal("15.99"), ExpenseType.FOOD);
            Expense expense4 = new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR);

            expenses.add(expense1);
            expenses.add(expense2);
            expenses.add(expense3);
            expenses.add(expense4);

            List<User> users= new ArrayList<>();

            User user1 = new User(1L, "Patrycja", 20);
            User user2 = new User(2L, "Fabian", 15);
            User user3 = new User(3L, "Lucjan", 25);
            users.add(user1);
            users.add(user2);
            users.add(user3);

            //when
            Map<User, List<Expense>> userExpensesListMap = streamsTask.groupExpensesByUser(users, expenses);

            //then
            assertThat(userExpensesListMap)
                    .containsEntry(user2, List.of(expense4));
        }

        @Test
        public void shouldReturnMapExpensesByUserWithCorrectExpenseListSize(){
            //given
            List<Expense> expenses = new ArrayList<>();
            expenses.add(new Expense(1L, "Buty", new BigDecimal("149.99"), ExpenseType.WEAR));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(1L, "Sałatka", new BigDecimal("14.99"), ExpenseType.FOOD));
            expenses.add(new Expense(2L, "Bluza", new BigDecimal("100"), ExpenseType.WEAR));

            List<User> users= new ArrayList<>();

            User user1 = new User(1L, "Patrycja", 20);
            User user2 = new User(2L, "Fabian", 15);
            User user3 = new User(3L, "Lucjan", 25);
            users.add(user1);
            users.add(user2);
            users.add(user3);

            //when
            Map<User, List<Expense>> userExpensesListMap = streamsTask.groupExpensesByUser(users, expenses);

            //then
            assertThat(userExpensesListMap.get(user1).size()).isEqualTo(3);
        }
    }
}
