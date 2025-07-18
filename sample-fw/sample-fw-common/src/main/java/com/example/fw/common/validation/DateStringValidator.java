package com.example.fw.common.validation;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 
 * 日付形式の文字列かどうか検証するValidatorクラス
 *
 */
public class DateStringValidator implements ConstraintValidator<DateString, CharSequence> {
    // 許容する日付のリストを定義
    private static final List<DateTimeFormatter> DATE_FORMATTERS = List.of(//
            DateTimeFormatter.ofPattern("yyyy-MM-dd"), //
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"), //
            DateTimeFormatter.ofPattern("yyyy/MM/dd"), //
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    // 日付文字列としての最小長
    private static final int MIN_LENGTH = 10;

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        // 必須入力チェックとして@NotBlankアノテーションと組み合わせて使用することを想定しているため、
        // nullまたは空列、空白の場合はtrueを返す
        if (value == null || value.toString().isBlank()) { // CharSequenceがtoString()を実装している前提
            return true;
        }
        // 最低文字列長の場合は日付形式でないと判断し、falseを返す        
        if (value.length() < MIN_LENGTH) {
            return false;
        }
        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                // 日付形式に変換できるかどうかを検証
                formatter.parse(value);
                return true;
            } catch (DateTimeParseException e) {
                // 変換できない場合は次のフォーマットを試すため何もしない
            }
        }

        return false;
    }

}
