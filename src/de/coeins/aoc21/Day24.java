package de.coeins.aoc21;

import java.util.Arrays;

public class Day24 implements Day {
    public static final int FIRST_LTTR = 'w';


    public long task1(String in) {
        return compute(in, false);
    }

    public long task2(String in) {
        return compute(in, true);
    }

    public static long compute(String inputlines, boolean smallest) {

        String[] lines = inputlines.split("\n");
        Command[] prog = new Command[lines.length];
        for (int i = 0; i < lines.length; i++) {
            prog[i] = compile(lines[i]);
        }

        // the following is highly specific for the MONAD software and won't work on anything else.

        int[] a = new int[14];
        int[] b = new int[14];
        int[] c = new int[14];
        for (int i = 0; i < 14; i++) {
            a[i] = prog[18 * i + 4].b;
            b[i] = prog[18 * i + 5].b;
            c[i] = prog[18 * i + 15].b;
        }

        int[] code = forceNumber(a, b, c, smallest, 0, new int[14]);
        System.out.println(Arrays.toString(code));

        long result = 0;
        for (int i = 0; i < code.length; i++) {
            result *=10;
            result += code[i];
        }

        // okay we've got our umber. Let's verify with the original MONAD just for fun.

        if (run(prog, code).get(3) != 0)
            throw new RuntimeException("Invalid code");

        return result;
    }

    private static int[] forceNumber(int[] a, int[] b, int[] c, boolean smallest, int layer, int[] code) {
        if (layer >= 14) {
            long x, x1, z = 0;
            int[] input = code.clone();
            for (int i = 0; i < 14; i++) {
                x1 = z % 26 + b[i];
                if (input[i] == 0) {
                    if (x1 < 1 || x1 > 9)
                        return null;
                    else
                        input[i] = (int) x1;
                }
                x = x1 != input[i] ? 1 : 0;
                z = (z / a[i]) * (25 * x + 1) + (input[i] + c[i]) * x;
            }
            if (z == 0) {
//                System.out.println(Arrays.toString(input));
                return input;
            } else
                return null;
        } else if (layer == 0 || b[layer] >= 10) {
            int[] result = null;
            for (int i = 1; i <= 9; i++) {
                code[layer] = i;
                result = compare(result, forceNumber(a, b, c, smallest, layer + 1, code), smallest);
            }
            return result;
        } else {
            return forceNumber(a, b, c, smallest, layer + 1, code);
        }
    }

    private static int[] compare(int[] x, int[] y, boolean smallest) {
        if (x == null) return y;
        if (y == null) return x;
        for (int i = 0; i < x.length; i++) {
            if (x[i] < y[i] && smallest || x[i] > y[i] && !smallest) return x;
            else if (x[i] > y[i] && smallest || x[i] < y[i] && !smallest) return y;
        }
        return x;
    }

    private static Command compile(String in) {
        String[] parts = in.split(" ");
        switch (parts[0]) {
            case "inp":
                return new INP(parts[1].charAt(0) - FIRST_LTTR);
            case "add":
                if (parts[2].charAt(0) >= FIRST_LTTR)
                    return new ADD(parts[1].charAt(0) - FIRST_LTTR, parts[2].charAt(0) - FIRST_LTTR, false);
                else
                    return new ADD(parts[1].charAt(0) - FIRST_LTTR, Integer.parseInt(parts[2]), true);
            case "mul":
                if (parts[2].charAt(0) >= FIRST_LTTR)
                    return new MUL(parts[1].charAt(0) - FIRST_LTTR, parts[2].charAt(0) - FIRST_LTTR, false);
                else
                    return new MUL(parts[1].charAt(0) - FIRST_LTTR, Integer.parseInt(parts[2]), true);
            case "div":
                if (parts[2].charAt(0) >= FIRST_LTTR)
                    return new DIV(parts[1].charAt(0) - FIRST_LTTR, parts[2].charAt(0) - FIRST_LTTR, false);
                else
                    return new DIV(parts[1].charAt(0) - FIRST_LTTR, Integer.parseInt(parts[2]), true);
            case "mod":
                if (parts[2].charAt(0) >= FIRST_LTTR)
                    return new MOD(parts[1].charAt(0) - FIRST_LTTR, parts[2].charAt(0) - FIRST_LTTR, false);
                else
                    return new MOD(parts[1].charAt(0) - FIRST_LTTR, Integer.parseInt(parts[2]), true);
            case "eql":
                if (parts[2].charAt(0) >= FIRST_LTTR)
                    return new EQL(parts[1].charAt(0) - FIRST_LTTR, parts[2].charAt(0) - FIRST_LTTR, false);
                else
                    return new EQL(parts[1].charAt(0) - FIRST_LTTR, Integer.parseInt(parts[2]), true);
            default:
                throw new RuntimeException("nö");


        }
    }

    private static CPUstate run(Command[] prog, int[] input) {
        CPUstate current = new CPUstate(input);
        for (Command command : prog) {
            current = command.execute(current);
//            System.out.println(current);
        }
        return current;
    }

    private static class CPUstate {
        private final int[] registers;
        private final int[] inputs;
        int pointer;

        public CPUstate(int[] inputs) {
            this(new int[4], inputs, 0);
        }

        public CPUstate(int[] reg, int[] inp, int ptr) {
            registers = reg;
            inputs = inp;
            pointer = ptr;
        }

        public int get(int r) {
            return registers[r];
        }

        public CPUstate set(int r, int v) {
            int[] reg = registers.clone();
            reg[r] = v;
            return new CPUstate(reg, inputs, pointer);
        }

        public CPUstate consumeInput() {
            return new CPUstate(registers, inputs, pointer + 1);
        }

        public int nextInput() {
            return (pointer < inputs.length) ? inputs[pointer] : -99999;
        }

        @Override
        public String toString() {
            return "{ reg: " + Arrays.toString(registers) +
                    " next: " + nextInput() + " }";
        }
    }

    private static abstract class Command {
        final int a;
        final int b;
        final boolean con;

        public Command(int a, int b, boolean con) {
            this.a = a;
            this.b = b;
            this.con = con;
        }

        public CPUstate execute(CPUstate before) {
            return before.set(a, calculate(before.get(a), (con ? b : before.get(b))));

        }

        abstract int calculate(int a, int b);

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append(" ");
            sb.append((char) (a + FIRST_LTTR)).append(" ");
            if (con) sb.append(b);
            else sb.append((char) (b + FIRST_LTTR));
            return sb.toString();
        }
    }

    private static class INP extends Command {
        public INP(int a) {
            super(a, 0, true);
        }

        @Override
        public CPUstate execute(CPUstate before) {
            return before.set(a, before.nextInput()).consumeInput();
        }

        @Override
        int calculate(int a, int b) {
            throw new RuntimeException("blä");
        }
    }

    private static class ADD extends Command {
        public ADD(int a, int b, boolean con) {
            super(a, b, con);
        }

        @Override
        int calculate(int a, int b) {
            return a + b;
        }

    }

    private static class MUL extends Command {

        public MUL(int a, int b, boolean con) {
            super(a, b, con);
        }

        @Override
        int calculate(int a, int b) {
            return a * b;
        }

    }

    private static class DIV extends Command {
        public DIV(int a, int b, boolean con) {
            super(a, b, con);
        }

        @Override
        int calculate(int a, int b) {
            return a / b;
        }

    }

    private static class MOD extends Command {
        public MOD(int a, int b, boolean con) {
            super(a, b, con);
        }

        @Override
        int calculate(int a, int b) {
            return a % b;
        }

    }

    private static class EQL extends Command {
        public EQL(int a, int b, boolean con) {
            super(a, b, con);
        }

        @Override
        int calculate(int a, int b) {
            return a == b ? 1 : 0;
        }

    }

}
