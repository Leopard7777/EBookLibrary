package mini.ebooklibrary.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Isbn13Generator
 */
public class IsbnGenerator {
    /**
     * ISBN-13 번호를 랜덤으로 생성합니다.
     *
     * @return 유효한 ISBN-13 문자열 (13자리)
     */
    public static String generateISBN13() {
        String prefix = ThreadLocalRandom.current().nextBoolean() ? "978" : "979";
        StringBuilder sb = new StringBuilder(prefix);
        // 접두사(3자리) 이후 총 12자리 숫자를 채웁니다.
        for (int i = 0; i < 9; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        // 체크 디지트 계산
        int check = calculateCheckDigit(sb.toString());
        sb.append(check);
        return sb.toString();
    }

    /**
     * ISBN-13 체크 디지트를 계산합니다.
     *
     * @param isbn12 앞 12자리 숫자 문자열
     * @return 체크 디지트 (0~9)
     */
    private static int calculateCheckDigit(String isbn12) {
        int sum = 0;
        for (int i = 0; i < isbn12.length(); i++) {
            int digit = Character.getNumericValue(isbn12.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int mod = sum % 10;
        return (mod == 0) ? 0 : 10 - mod;
    }

//    public static void main(String[] args) {
//        // 예시 실행
//        String isbn = generateISBN13();
//        System.out.println("Generated ISBN-13: " + isbn);
//    }
}
