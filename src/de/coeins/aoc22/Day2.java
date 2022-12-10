package de.coeins.aoc22;

class Day2 implements Day<Integer> {
   @Override
   public Integer task1(String[] in) {
      int result = 0;
      for (String i:  in) {
         switch (i) {
            case "A X": result += 3 + 1; break; // r,r,d
            case "B X": result += 0 + 1; break; // p,r,l
            case "C X": result += 6 + 1; break; // s,r,w

            case "A Y": result += 6 + 2; break; // r,p,w
            case "B Y": result += 3 + 2; break; // p,p,d
            case "C Y": result += 0 + 2; break; // s,p,l

            case "A Z": result += 0 + 3; break; // r,s,l
            case "B Z": result += 6 + 3; break; // p,s,w
            case "C Z": result += 3 + 3; break; // s,s,d
            default: throw new RuntimeException("Unknonw input combination");
         }
      }
      return result;
   }

   @Override
   public Integer task2(String[] in) {
      int result = 0;
      for (String i:  in) {
         switch (i) {
            case "A X": result += 0 + 3; break; // r,s,l
            case "B X": result += 0 + 1; break; // p,r,l
            case "C X": result += 0 + 2; break; // s,p,l

            case "A Y": result += 3 + 1; break; // r,r,d
            case "B Y": result += 3 + 2; break; // p,p,d
            case "C Y": result += 3 + 3; break; // s,s,d

            case "A Z": result += 6 + 2; break; // r,p,w
            case "B Z": result += 6 + 3; break; // p,s,w
            case "C Z": result += 6 + 1; break; // s,r,w
            default: throw new RuntimeException("Unknonw input combination");
         }
      }
      return result;
   }
}
