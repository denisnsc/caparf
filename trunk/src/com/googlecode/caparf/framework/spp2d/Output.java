package com.googlecode.caparf.framework.spp2d;

import java.util.List;

import com.googlecode.caparf.framework.base.BaseOutput;

/**
 * Output for 2 Dimensional Strip Packing Problem.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class Output extends BaseOutput<RectanglePlacement> {

  /** Corresponding input. */
  private Input input;

  /**
   * Constructs output for 2 Dimensional Strip Packing Problem. Placements is the
   * ordered list of bottom-left points of corresponding rectangle items.
   *
   * @param input corresponding input for 2 Dimensional Strip Packing Problem
   * @param placements rectangle item placements
   */
  public Output(Input input, List<RectanglePlacement> placements) {
    super(placements);
    this.input = input;
  }

  @Override
  public Number calculateObjectiveFunction() {
    int stripHeight = 0;
    for (int i = 0; i < getPlacementsCount(); i++) {
      stripHeight = Math.max(stripHeight, getPlacements().get(i).getY() +
          input.getItems().get(i).getHeight());
    }
    return stripHeight;
  }

  @Override
  public void transform(List<Integer> transformation) {
    super.transform(transformation);
    input.transform(transformation);
  }

  @Override
  public String toString() {
    String ret = "";
    for (RectanglePlacement placement : getPlacements()) {
      if (ret.length() > 0) {
        ret += ", ";
      }
      ret += "(" + placement.getX() + ", " + placement.getY() + ")";
    }
    return ret;
  }
}
