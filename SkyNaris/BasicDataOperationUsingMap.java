import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Клас BasicDataOperationUsingMap реалізує операції з колекціями типу Map.
 * Адаптовано для варіанту: Gerbil (HashMap vs TreeMap).
 */
public class BasicDataOperationUsingMap {
    // Константи згідно з варіантом завдання
    private final Gerbil KEY_TO_SEARCH_AND_DELETE = new Gerbil("Бісквіт", 12);
    private final Gerbil KEY_TO_ADD = new Gerbil("Мікс", 20);

    private final String VALUE_TO_SEARCH_AND_DELETE = "Анна";
    private final String VALUE_TO_ADD = "Тарас";

    // Замінили Hashtable на HashMap
    private HashMap<Gerbil, String> hashMap;
    private TreeMap<Gerbil, String> treeMap;

    /**
     * Компаратор для сортування Map.Entry за значеннями String (імена власників).
     */
    static class OwnerValueComparator implements Comparator<Map.Entry<Gerbil, String>> {
        @Override
        public int compare(Map.Entry<Gerbil, String> e1, Map.Entry<Gerbil, String> e2) {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            if (v1 == null && v2 == null) return 0;
            if (v1 == null) return -1;
            if (v2 == null) return 1;
            return v1.compareTo(v2);
        }
    }

    /**
     * Внутрішній клас Gerbil (Піщанка).
     */
    public static class Gerbil implements Comparable<Gerbil> {
        private final String nickname;
        private final Integer food; 

        public Gerbil(String nickname, Integer food) {
            this.nickname = nickname;
            this.food = food;
        }

        public String getNickname() {
            return nickname;
        }

        public Integer getFood() {
            return food;
        }

        @Override
        public int compareTo(Gerbil other) {
            if (other == null) return 1;

            // 1. Порівняння за кличкою (за зростанням)
            int nicknameComparison = 0;
            if (this.nickname == null && other.nickname == null) {
                nicknameComparison = 0;
            } else if (this.nickname == null) {
                nicknameComparison = -1;
            } else if (other.nickname == null) {
                nicknameComparison = 1;
            } else {
                nicknameComparison = this.nickname.compareTo(other.nickname);
            }

            if (nicknameComparison != 0) {
                return nicknameComparison;
            }

            // 2. Порівняння за їжею (за зростанням)
            if (this.food == null && other.food == null) return 0;
            if (this.food == null) return -1;
            if (other.food == null) return 1;
            return this.food.compareTo(other.food);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Gerbil gerbil = (Gerbil) obj;
            boolean nicknameEquals = nickname != null ? nickname.equals(gerbil.nickname) : gerbil.nickname == null;
            boolean foodEquals = food != null ? food.equals(gerbil.food) : gerbil.food == null;
            return nicknameEquals && foodEquals;
        }

        @Override
        public int hashCode() {
            int result = nickname != null ? nickname.hashCode() : 0;
            result = 31 * result + (food != null ? food.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Gerbil{nickname='" + nickname + "', food=" + food + "}";
        }
    }

    /**
     * Конструктор.
     */
    BasicDataOperationUsingMap(HashMap<Gerbil, String> hashMap, TreeMap<Gerbil, String> treeMap) {
        this.hashMap = hashMap;
        this.treeMap = treeMap;
    }

    /**
     * Виконує комплексні операції з Map.
     */
    public void executeDataOperations() {
        // --- Операції з HashMap ---
        System.out.println("========= Операції з HashMap =========");
        System.out.println("Початковий розмір HashMap: " + hashMap.size());

        findByKeyInHashMap();
        findByValueInHashMap();

        printHashMap();
        sortHashMap(); 
        printHashMap(); 

        findByKeyInHashMap(); 
        findByValueInHashMap();

        addEntryToHashMap();

        removeByKeyFromHashMap();
        removeByValueFromHashMap();

        System.out.println("Кінцевий розмір HashMap: " + hashMap.size());

        // --- Операції з TreeMap ---
        System.out.println("\n\n========= Операції з TreeMap =========");
        System.out.println("Початковий розмір TreeMap: " + treeMap.size());

        findByKeyInTreeMap();
        findByValueInTreeMap();

        printTreeMap();

        addEntryToTreeMap();
        
        removeByKeyFromTreeMap();
        removeByValueFromTreeMap();
        
        System.out.println("Кінцевий розмір TreeMap: " + treeMap.size());
    }

    // ===== Методи для HashMap =====

    private void printHashMap() {
        System.out.println("\n=== Пари ключ-значення в HashMap ===");
        long timeStart = System.nanoTime();

        for (Map.Entry<Gerbil, String> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }

        PerformanceTracker.displayOperationTime(timeStart, "виведення пари ключ-значення в HashMap");
    }

    private void sortHashMap() {
        long timeStart = System.nanoTime();
        List<Gerbil> sortedKeys = new ArrayList<>(hashMap.keySet());
        Collections.sort(sortedKeys);
        LinkedHashMap<Gerbil, String> sortedMap = new LinkedHashMap<>();
        for (Gerbil key : sortedKeys) {
            sortedMap.put(key, hashMap.get(key));
        }
        hashMap = sortedMap;
        PerformanceTracker.displayOperationTime(timeStart, "сортування HashMap за ключами");
    }

    void findByKeyInHashMap() {
        long timeStart = System.nanoTime();
        boolean found = hashMap.containsKey(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в HashMap");

        if (found) {
            String value = hashMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    void findByValueInHashMap() {
        long timeStart = System.nanoTime();
        List<Map.Entry<Gerbil, String>> entries = new ArrayList<>(hashMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);

        Map.Entry<Gerbil, String> searchEntry = new Map.Entry<Gerbil, String>() {
            public Gerbil getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };

        int position = Collections.binarySearch(entries, searchEntry, comparator);
        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в HashMap");

        if (position >= 0) {
            Map.Entry<Gerbil, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Gerbil: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в HashMap.");
        }
    }

    void addEntryToHashMap() {
        long timeStart = System.nanoTime();
        hashMap.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до HashMap");
        System.out.println("Додано новий запис: Gerbil='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    void removeByKeyFromHashMap() {
        long timeStart = System.nanoTime();
        String removedValue = hashMap.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з HashMap");

        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    void removeByValueFromHashMap() {
        long timeStart = System.nanoTime();
        List<Gerbil> keysToRemove = new ArrayList<>();
        for (Map.Entry<Gerbil, String> entry : hashMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (Gerbil key : keysToRemove) {
            hashMap.remove(key);
        }
        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з HashMap");
        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    // ===== Методи для TreeMap =====

    private void printTreeMap() {
        System.out.println("\n=== Пари ключ-значення в TreeMap ===");
        long timeStart = System.nanoTime();
        for (Map.Entry<Gerbil, String> entry : treeMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        PerformanceTracker.displayOperationTime(timeStart, "виведення пар ключ-значення в TreeMap");
    }

    void findByKeyInTreeMap() {
        long timeStart = System.nanoTime();
        boolean found = treeMap.containsKey(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "пошук за ключем в TreeMap");
        if (found) {
            String value = treeMap.get(KEY_TO_SEARCH_AND_DELETE);
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' знайдено. Власник: " + value);
        } else {
            System.out.println("Елемент з ключем '" + KEY_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    void findByValueInTreeMap() {
        long timeStart = System.nanoTime();
        List<Map.Entry<Gerbil, String>> entries = new ArrayList<>(treeMap.entrySet());
        OwnerValueComparator comparator = new OwnerValueComparator();
        Collections.sort(entries, comparator);
        Map.Entry<Gerbil, String> searchEntry = new Map.Entry<Gerbil, String>() {
            public Gerbil getKey() { return null; }
            public String getValue() { return VALUE_TO_SEARCH_AND_DELETE; }
            public String setValue(String value) { return null; }
        };
        int position = Collections.binarySearch(entries, searchEntry, comparator);
        PerformanceTracker.displayOperationTime(timeStart, "бінарний пошук за значенням в TreeMap");
        if (position >= 0) {
            Map.Entry<Gerbil, String> foundEntry = entries.get(position);
            System.out.println("Власника '" + VALUE_TO_SEARCH_AND_DELETE + "' знайдено. Gerbil: " + foundEntry.getKey());
        } else {
            System.out.println("Власник '" + VALUE_TO_SEARCH_AND_DELETE + "' відсутній в TreeMap.");
        }
    }

    void addEntryToTreeMap() {
        long timeStart = System.nanoTime();
        treeMap.put(KEY_TO_ADD, VALUE_TO_ADD);
        PerformanceTracker.displayOperationTime(timeStart, "додавання запису до TreeMap");
        System.out.println("Додано новий запис: Gerbil='" + KEY_TO_ADD + "', власник='" + VALUE_TO_ADD + "'");
    }

    void removeByKeyFromTreeMap() {
        long timeStart = System.nanoTime();
        String removedValue = treeMap.remove(KEY_TO_SEARCH_AND_DELETE);
        PerformanceTracker.displayOperationTime(timeStart, "видалення за ключем з TreeMap");
        if (removedValue != null) {
            System.out.println("Видалено запис з ключем '" + KEY_TO_SEARCH_AND_DELETE + "'. Власник був: " + removedValue);
        } else {
            System.out.println("Ключ '" + KEY_TO_SEARCH_AND_DELETE + "' не знайдено для видалення.");
        }
    }

    void removeByValueFromTreeMap() {
        long timeStart = System.nanoTime();
        List<Gerbil> keysToRemove = new ArrayList<>();
        for (Map.Entry<Gerbil, String> entry : treeMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(VALUE_TO_SEARCH_AND_DELETE)) {
                keysToRemove.add(entry.getKey());
            }
        }
        for (Gerbil key : keysToRemove) {
            treeMap.remove(key);
        }
        PerformanceTracker.displayOperationTime(timeStart, "видалення за значенням з TreeMap");
        System.out.println("Видалено " + keysToRemove.size() + " записів з власником '" + VALUE_TO_SEARCH_AND_DELETE + "'");
    }

    /**
     * Головний метод для запуску програми.
     */
    public static void main(String[] args) {
        // Ініціалізація даних
        HashMap<Gerbil, String> hashMap = new HashMap<>();
        hashMap.put(new Gerbil("Арахіс", 15), "Олег");
        hashMap.put(new Gerbil("Бісквіт", 12), "Анна");
        hashMap.put(new Gerbil("Гризлик", 18), "Іван");
        hashMap.put(new Gerbil("Джеррі", 10), "Марія");
        hashMap.put(new Gerbil("Їжак", 14), "Петро");
        hashMap.put(new Gerbil("Бісквіт", 16), "Софія");
        hashMap.put(new Gerbil("Жуйка", 13), "Анна");
        hashMap.put(new Gerbil("Ірис", 11), "Дмитро");
        hashMap.put(new Gerbil("Кекс", 17), "Марія");
        hashMap.put(new Gerbil("Лопушок", 9), "Оксана");

        TreeMap<Gerbil, String> treeMap = new TreeMap<>();
        treeMap.put(new Gerbil("Арахіс", 15), "Олег");
        treeMap.put(new Gerbil("Бісквіт", 12), "Анна");
        treeMap.put(new Gerbil("Гризлик", 18), "Іван");
        treeMap.put(new Gerbil("Джеррі", 10), "Марія");
        treeMap.put(new Gerbil("Їжак", 14), "Петро");
        treeMap.put(new Gerbil("Бісквіт", 16), "Софія");
        treeMap.put(new Gerbil("Жуйка", 13), "Анна");
        treeMap.put(new Gerbil("Ірис", 11), "Дмитро");
        treeMap.put(new Gerbil("Кекс", 17), "Марія");
        treeMap.put(new Gerbil("Лопушок", 9), "Оксана");

        BasicDataOperationUsingMap operations = new BasicDataOperationUsingMap(hashMap, treeMap);
        operations.executeDataOperations();
    }
}