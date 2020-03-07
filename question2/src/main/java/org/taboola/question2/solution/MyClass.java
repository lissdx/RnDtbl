package org.taboola.question2.solution;

import java.util.*;
import java.util.stream.Collectors;
import static java.util.Objects.requireNonNull;

public class MyClass {

    private final Date m_time;   // never null
    private final String m_name; // never null
    /*
     * I know there are a lot of discussion about using the 'Optional'
     * type for class's fields but in my opinion it's a matter of taste, pragmatism
     * and an agreement in a team :). Than lets try it :)
     */
    private final Optional<List<Long>> m_numbers; // optional

    // TODO: remove this field
    private final  List<String> m_strings = null; // useless in class and
                                                  // no longer used in public interface

    /**
     * Store Compatibility Constructor:
     * This one is stored for backward compatibility
     * @deprecated The 'string' field is no longer supported
     */
    @Deprecated
    public MyClass(Date time, String name, List<Long> numbers, List<String> strings) {
        this(time, name, Optional.of(
                new ArrayList<>(requireNonNull(numbers, "The argument 'numbers' must not be null."))
        ));
    }

    /*
     * New MyClass's main constructors:
     */
    public MyClass(Date time, String name, List<Long> numbers) {
        this(time, name, Optional.of(
                new ArrayList<>(requireNonNull(numbers, "The argument 'numbers' must not be null."))
        ));
    }

    /*
     * Constructors:
     * Allow create obj with empty 'numbers' field
     */
    public MyClass(Date time, String name ) {
        this(time, name, Optional.empty());
    }

    /*
     * Constructors:
     * Optional param added
     * Also may have a protected access
     */
    private MyClass(Date time, String name, Optional<List<Long>> numbers) {
        m_time = requireNonNull(time, "The argument 'time' must not be null.");
        m_name = requireNonNull(name, "The argument 'name' must not be null.");
        // For internal usage.
        // Check 'Optional' param not null.
        // Relevant for public constructor
        m_numbers = requireNonNull(numbers, "The argument 'numbers' must not be null.");
    }

    /*
     * About equals... probably it's good enough
     * I gonna improve the comparison and add
     * all fields for the process
     *
     * Kind of 'Classic Way'
     */
    @Override
    public boolean equals(Object o) {

        if (o == this) return true;

        if (!(o instanceof MyClass)) {
            return false;
        }

        MyClass myClass = (MyClass) o;

        return myClass.m_name.equals(m_name) &&
                myClass.m_time.equals(m_time) &&
                containsAll(myClass.m_numbers.orElse(null), m_numbers.orElse(null));
    }

    // The field m_date is not presented in
    // the string.
    // I don't know why, but it works :)
    public String toString() {
        String numberStr = m_numbers
                .map(longs -> longs.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")))
                .filter( s -> !s.isEmpty() )
                .map( s -> new StringBuffer().append(" ").append(s).toString())
                .orElse("");

        return new StringBuffer()
                .append(m_name)
                .append(numberStr).toString();
    }

    /**
     * Have no affect. The internal field
     * gonna be changed but it haven't public
     * access and useless in the class
     * @deprecated Have no longer affect
     */
    @Deprecated
    public void removeString(String str) {
        // TODO: clean code
//        for (int i = 0; i < m_strings.size(); i++) {
//            if (m_strings.get(i).equals(str)) {
//                m_strings.remove(i);
//            }
//        }
    }

    public boolean containsNumber(long number) {
        return m_numbers
                .map(longs -> longs.contains(number))
                .orElse(false);
    }

    public boolean isHistoric() {
        return m_time.before(new Date());
    }

    //
    // There list comparator complicity O(N)
    //
    private boolean containsAll(List<Long> left, List<Long> right){
        // Both lists are null
        if( null == right || null == left ){
            return right == left;
        } else if( left.size() != right.size()) // Both lists have the same size
            return false;

        // Remove null
        List<Long> leftNorm  = left.stream().filter( Objects::nonNull ).collect(Collectors.toList());
        List<Long> rightNorm = right.stream().filter( Objects::nonNull ).collect(Collectors.toList());

        // Another length compare??? Yep!
        // It means we've deleted
        // the same number o null obj
        if( leftNorm.size() != rightNorm.size())
            return false;

        // Here both of lists have same size
        // lets compare them

        // 1. Create map where the key
        // will be Long-value and value
        // presentation counting
        // I mean Map: (Long)key -> cnt
        HashMap<Long, Integer> hash= new HashMap<>();
        for (Long aLong : leftNorm) {
            int cnt = hash.getOrDefault(aLong, 0) + 1;
            hash.put(aLong, cnt);
        }

        // 2. lets check if ALL right
        // list values are presented in
        // the left and count them
        for (Long bLong : rightNorm) {
            int cnt = hash.getOrDefault(bLong, 0) - 1;
            if( cnt >= 0 )
                hash.put(bLong, cnt);
            else
                return false; // Wow negative value found!
                              // they are different
        }

        return true;
    }
}