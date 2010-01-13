package com.googlecode.caparf.algorithms.spp2d.lowerbounds;

import com.googlecode.caparf.framework.base.LowerBound;
import com.googlecode.caparf.framework.spp2d.Input;

/**
 * Lower bound that was proposed by Carlier et al. in <a
 * href="http://linkinghub.elsevier.com/retrieve/pii/S0305054805002765">"New
 * reduction procedures and lower bounds for the two-dimensional bin packing
 * problem with fixed orientation"</a>. This lower bound is a slight improvement
 * on the function of Boschetti in <a
 * href="http://linkinghub.elsevier.com/retrieve/pii/S0166218X03005559"
 * >"New lower bounds for the three-dimensional finite bin packing problem"</a>.
 * Comparison of CCM lower bound with other lower bounds can be found in <a
 * href="http://www.springerlink.com/content/l2602pq543212730/">
 * "A survey of dual-feasible and superadditive functions"</a>.
 * <p>
 * Time complexity of this implementaion of CCM lower bound is {@code O(n * C)},
 * where {@code C} is the strip width.
 * <p>
 * This class is thread-safe.
 * 
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class CarlierClautiauxMoukrimBound implements LowerBound<Input> {

  @Override
  public Number calculateLowerBound(Input input) {
    int ret = 0;
    for (int k = 1; k <= input.getStripWidth(); k++) {
      ret = Math.max(ret, getBound(input, k));
    }
    return ret;
  }

  /** Calculates lower bound for the given parameter {@code k}. */
  private int getBound(Input input, int k) {
    int totalArea = 0;
    for (int i = 0; i < input.getItemsCount(); i++) {
      totalArea += input.getItems().get(i).getHeight() *
          f(input.getItems().get(i).getWidth(), input.getStripWidth(), k);
    }
    int ck = 2 * (input.getStripWidth() / k);
    int ret = totalArea / ck;
    if (totalArea % ck > 0) {
      ret += 1;
    }
    return ret;
  }

  /**
   * Calculates value of CCM dual-feasible function for the given argument
   * {@code x} and parameters {@code c} and {@code k}.
   */
  private int f(int x, int c, int k) {
    if (2 * x > c) {
      return 2 * (c / k) - 2 * (c - x) / k;
    } else if (2 * x < c) {
      return 2 * (x / k);
    } else {
      return c / k;
    }
  }
}
