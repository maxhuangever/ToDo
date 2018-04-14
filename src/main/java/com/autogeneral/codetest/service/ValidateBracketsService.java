package com.autogeneral.codetest.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class ValidateBracketsService {
    private static char[] OPEN_BRACKETS = new char[]{'(', '[', '{'};
    private static char[] CLOSE_BRACKETS = new char[]{')', ']', '}'};
    private static Map<Character, Character> BRACKETS = new HashMap<>();

    static {
        for (int i = 0; i < OPEN_BRACKETS.length; i++) {
            BRACKETS.put(OPEN_BRACKETS[i], CLOSE_BRACKETS[i]);
        }
    }

    public boolean checkBalance(String bracketStr) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < bracketStr.length(); i++) {
            char c = bracketStr.charAt(i);
            if (isOpenBracket(c)) {
                stack.push(c);
            } else if (isCloseBracket(c)) {
                if (stack.isEmpty()) {
                    return false;
                }
                if (getCloseBracket(stack.pop()) != c) {
                    return false;
                }
            }
        }

        if (stack.empty()) {
            return true;
        }

        return false;
    }

    private boolean isOpenBracket(char c) {
        return BRACKETS.containsKey(c);
    }

    private boolean isCloseBracket(char c) {
        return BRACKETS.containsValue(c);
    }

    private char getCloseBracket(char c) {
        return BRACKETS.get(c);
    }
}
