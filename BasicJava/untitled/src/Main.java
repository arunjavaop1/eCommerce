//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

  void main() {
    String s = "";

    System.out.println(reverse(s));
  }

  String reverse(String str) {
    int si = 0, ei = str.length() - 1;

    char[] ch = str.toCharArray();

    while(si <= ei) {
      char t = ch[si];
      ch[si] = ch[ei];
      ch[ei] = t;
      si++;
      ei--;
    }

    return new String(ch);
  }

