package com.googlecode.caparf.framework.spp2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.caparf.framework.base.Algorithm;
import com.googlecode.caparf.framework.base.BaseOutputVerdict;
import com.googlecode.caparf.framework.base.BaseOutputVerdict.Verdict;
import com.googlecode.caparf.framework.base.InputSuite;
import com.googlecode.caparf.framework.runner.RunListener;
import com.googlecode.caparf.framework.runner.Scenario;

/**
 * Listener that collects statistics during 2-dimensional strip packing
 * algorithm's execution and displays it in text form.
 *
 * @author denis.nsc@gmail.com (Denis Nazarov)
 */
public class StatsCollectorListener extends RunListener<Input, Output, BaseOutputVerdict> {

  /** Lower bound used for calculating stats. */
  private final LowerBound lowerBound;

  /** Sorted list of algorithm display names. */
  private List<String> algorithmNames;

  /** Root of sorted input identifiers tree. */
  private Node root;

  public StatsCollectorListener(LowerBound lowerBound) {
    this.lowerBound = lowerBound;
  }

  @Override
  public void scenarioRunStarted(Scenario<Input, Output, BaseOutputVerdict> scenario)
      throws Exception {
    algorithmNames = new ArrayList<String>();
    for (Algorithm<Input, Output> algorithm : scenario.getAlgorithms()) {
      algorithmNames.add(algorithm.getDisplayName());
    }
    Collections.sort(algorithmNames);

    root = buildSortedInputsTree(scenario.getInputs());
  }

  @Override
  public void scenarioRunFinished() throws Exception {
    printTree(root, "");
  }

  @Override
  public void testFinished(Algorithm<Input, Output> algorithm, Input input, Output output,
      BaseOutputVerdict verdict) throws Exception {
    root.collect(input.getIdentifier(), algorithm.getDisplayName(), output, verdict);
  }

  /** Prints tree with statistics to stdout. */
  public void printTree(Node node, String prefix) {
    if (prefix.isEmpty() && node.getChildren().size() == 1) {
      printTree(node.getChildren().get(0), prefix);
    } else {
      if (prefix.isEmpty()) {
        System.out.print("\n" + String.format("%30s%-15s", " ", "LowerBound"));
        for (String id : algorithmNames) {
          System.out.print(String.format("%-15s", id));
        }
        System.out.print("\n" + String.format("%45s", " "));
        for (int i = 0; i < algorithmNames.size(); i++) {
          System.out.print(String.format("%-8s%-7s", "gap", "best"));
        }
        System.out.println();
      }
      System.out.print(String.format("%-30s%-15.2f",
          prefix + node.getName(),
          node.getLowerBoundSum() / (double) node.getInputsCount()));
      for (String id : algorithmNames) {
        System.out.print(String.format("%-8s%-7d",
            String.format("%.2f%%", node.getStats().getGap().get(id) / node.getInputsCount()),
            node.getStats().getBestCount().get(id)));
      }
      System.out.println();
      for (Node child : node.getChildren()) {
        if (child.getInputsCount() > 1) {
          printTree(child, prefix + "  ");
        }
      }
    }
  }

  /**
   * Builds sorted input identifiers tree with empty statistics and return its
   * root.
   *
   * @return root of sorted input identifiers tree.
   */
  private Node buildSortedInputsTree(InputSuite<Input> suite) {
    Node root = new Node("root");
    for (Input input : suite.getAll()) {
      root.addNode(input.getIdentifier(), lowerBound.calculateLowerBound(input));
    }
    root.normalize();
    return root;
  }

  /** Node of input identifiers tree. */
  public static class Node implements Comparable<Node> {
    /** Name of node. */
    protected final String name;

    /** List of node's children. */
    protected final List<Node> children;

    /** Statistics corresponding to the node. */
    protected final AlgorithmStats stats;

    /** Number of inputs in subtree rooted at this node. */
    protected int inputsCount;

    /** Sum of lower bounds for inputs in subtree rooted at this node. */
    protected int lowerBoundSum;

    /**
     * Best strip height achieved by algorithms. It makes sense only for leafs.
     */
    protected int bestHeight;

    /**
     * Creates tree node with the given name.
     *
     * @param name node name
     */
    public Node(String name) {
      this.name = name;
      this.children = new ArrayList<Node>();
      this.inputsCount = this.lowerBoundSum = 0;
      this.bestHeight = Integer.MAX_VALUE;
      this.stats = new AlgorithmStats();
    }

    /**
     * Recursively adds nodes for the given part of input identifier.
     *
     * @param id part of input identifier
     */
    public void addNode(String id, int lowerBound) {
      inputsCount += 1;
      lowerBoundSum += lowerBound;

      int dotIndex = id.indexOf('.');
      boolean isLeaf = dotIndex == -1;

      String childName = isLeaf ? id : id.substring(0, dotIndex);
      Node child = getChild(childName);
      if (child == null) {
        child = createChild(childName);
      } else if (isLeaf) {
        throw new IllegalArgumentException(
            "Inputs must have unique identifiers within single scenario");
      }

      if (isLeaf) {
        child.inputsCount = 1;
        child.lowerBoundSum = lowerBound;
      } else {
        child.addNode(id.substring(dotIndex + 1), lowerBound);
      }
     }

    /**
     * Finds child of the node by the given name.
     *
     * @param name name of child to find
     * @return child with the given name of null if there is no such child
     */
    public Node getChild(String name) {
      for (Node node : children) {
        if (node.getName().equals(name)) {
          return node;
        }
      }
      return null;
    }

    /**
     * Creates child of the node by the given name.
     *
     * @param name name of child to create
     * @return
     */
    public Node createChild(String name) {
      Node child = new Node(name);
      children.add(child);
      return child;
    }

    /** Normalize tree by sorting children of all nodes. */
    public void normalize() {
      for (Node node : children) {
        node.normalize();
      }
      Collections.sort(children);
    }

    /**
     * Collects statistics.
     *
     * @param id part of input identifier
     * @param algorithmName algorithm name
     * @param output output produced by algorithm
     * @param verdict output verification verdict
     */
    public void collect(String id, String algorithmName, Output output, BaseOutputVerdict verdict) {
      int dotIndex = id.indexOf('.');
      String name = dotIndex == -1 ? id : id.substring(0, dotIndex);
      Node child = getChild(name);
      if (child == null) {
        throw new IllegalArgumentException("Inputs must not be modified during scenario execution");
      }
      if (dotIndex == -1) {
        child.stats.collect(algorithmName, output, verdict);
      } else {
        child.collect(id.substring(dotIndex + 1), algorithmName, output, verdict);
        child.stats.update(algorithmName);
      }
    }

    /**
     * Returns node name. Join node names on the path from tree root to leaf by
     * dots in order to reconstruct input identifier.
     *
     * @return node name
     */
    public String getName() {
      return name;
    }

    /**
     * @return statistics corresponding to the node
     */
    public AlgorithmStats getStats() {
      return stats;
    }

    /**
     * @return number of inputs in subtree rooted at this node
     */
    public int getInputsCount() {
      return inputsCount;
    }

    /**
     * @return sum of lower bounds for inputs in subtree rooted at this node
     */
    public int getLowerBoundSum() {
      return lowerBoundSum;
    }

    /**
     * @return children of current node
     */
    public List<Node> getChildren() {
      return Collections.unmodifiableList(children);
    }

    @Override
    public int compareTo(Node o) {
      return this.name.compareTo(o.name);
    }

    /** Algorithm statistics. */
    public class AlgorithmStats {
      /** Sum of heights per algorithm. */
      protected Map<String, Integer> height;

      /** Best outputs count per algorithm. */
      protected Map<String, Integer> bestCount;

      /** Sum of gaps per algorithm. */
      protected Map<String, Double> gap;

      public AlgorithmStats() {
        height = new HashMap<String, Integer>();
        bestCount = new HashMap<String, Integer>();
        gap = new HashMap<String, Double>();
      }

      /**
       * Collects statistics for the algorithm with name {@code algorithmName}.
       * This should be called only for leafs.
       *
       * @param algorithmName name of algorithm
       * @param output output produced by algorithm
       * @param verdict output verification verdict
       */
      public void collect(String algorithmName, Output output, BaseOutputVerdict verdict) {
        if (verdict.getVerdict() != Verdict.VALID_OUTPUT) {
          return;
        }
        int stripHeight = output.calculateObjectiveFunction().intValue();
        height.put(algorithmName, stripHeight);
        gap.put(algorithmName, (stripHeight - lowerBoundSum) * 100.0 / stripHeight);
        bestHeight = Math.min(bestHeight, stripHeight);
        for (String id : height.keySet()) {
          bestCount.put(id, height.get(id) == bestHeight ? 1 : 0);
        }
      }

      /**
       * Updates statistics for the current node. This should be called only for
       * non-leafs.
       */
      public void update(String algorithmName) {
        height.put(algorithmName, Integer.MAX_VALUE);
        for (String id : height.keySet()) {
          int heightSum = 0;
          int bestCountSum = 0;
          double gapSum = 0.0;
          for (Node node : children) {
            if (node.stats.height.containsKey(id)) {
              heightSum += node.stats.height.get(id);
              bestCountSum += node.stats.bestCount.get(id);
              gapSum += node.stats.gap.get(id);
            }
          }
          height.put(id, heightSum);
          bestCount.put(id, bestCountSum);
          gap.put(id, gapSum);
        }
      }

      /**
       * @return sum of heights per algorithm
       */
      public Map<String, Integer> getHeight() {
        return height;
      }

      /**
       * @return best outputs count per algorithm
       */
      public Map<String, Integer> getBestCount() {
        return bestCount;
      }

      /**
       * @return sum of gaps per algorithm
       */
      public Map<String, Double> getGap() {
        return gap;
      }
    }
  }
}
