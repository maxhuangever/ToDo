package com.autogeneral.codetest.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class ValidateBracketsServiceTest {
    ValidateBracketsService validateBracketsService = new ValidateBracketsService();

    @Parameterized.Parameters(name = "{index}: \"{0}\" balance check will return {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"[]", true},
                {" [ ] ", true},
                {" {([ ])} ", true},
                {"[{(){}}]", true},
                {"{{}{}{}}", true},
                {"[", false},
                {" {([( ])} ", false},
                {" {[(])} ", false},
                {"wfs'fs[{{{(s;dkls(dslkf)s;dlkf}]}]}}}sd", false},
                {"wfs'fs[{{{(s;dkls(dslkf)s;dlkf)}}}]sd", true},
                {"][", false},
                {"(][)", false}
        });
    }

    @Parameterized.Parameter(0)
    public String bracketStr;

    @Parameterized.Parameter(1)
    public boolean isBalanced;

    @Test
    public void testCheckBalance_givenDifferentStrings() {
        Assert.assertEquals(validateBracketsService.checkBalance(bracketStr), isBalanced);
    }
}
