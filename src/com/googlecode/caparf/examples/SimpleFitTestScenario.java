/*
 * Copyright (C) 2010 Denis Nazarov <denis.nsc@gmail.com>.
 *
 * This file is part of caparf (http://code.google.com/p/caparf/).
 *
 * caparf is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * caparf is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with caparf. If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.caparf.examples;

import com.googlecode.caparf.algorithms.spp2d.SimpleFit;
import com.googlecode.caparf.algorithms.spp2d.SimpleFit.ItemOrder;
import com.googlecode.caparf.algorithms.spp2d.SimpleFit.PlacementStrategy;
import com.googlecode.caparf.algorithms.spp2d.lowerbounds.ContinuousBound;
import com.googlecode.caparf.framework.base.InputSuite;
import com.googlecode.caparf.framework.runner.CaparfCore;
import com.googlecode.caparf.framework.runner.Scenario;
import com.googlecode.caparf.framework.runner.StatsCollectorListener;
import com.googlecode.caparf.framework.spp2d.Input;
import com.googlecode.caparf.framework.spp2d.Output;
import com.googlecode.caparf.framework.spp2d.OutputVerifier;
import com.googlecode.caparf.inputs.bpp2d.BerkeyWangGenerator;
import com.googlecode.caparf.inputs.bpp2d.MartelloVigoGenerator;
import com.googlecode.caparf.inputs.spp2d.Converter;

public class SimpleFitTestScenario {

  public static void main(String[] args) {
    // Create scenario instance
    Scenario<Input, Output> scenario = new Scenario<Input, Output>();
    scenario.setTimeLimit(5000);

    // Add algorithms to scenario
    scenario.addAlgorithms(
        new SimpleFit(ItemOrder.NEXT_ITEM, PlacementStrategy.DEFAULT),
        new SimpleFit(ItemOrder.NEXT_ITEM, PlacementStrategy.SHIFT_RIGHTMOST_ITEM),
        new SimpleFit(ItemOrder.FIRST_FIT, PlacementStrategy.DEFAULT),
        new SimpleFit(ItemOrder.FIRST_FIT, PlacementStrategy.SHIFT_RIGHTMOST_ITEM));

    // Create suite of Bortfeld set (Berkey-Wang and Martello-Vigo inputs)
    // and add it to scenario
    InputSuite<Input> berkeyWangSuite = new InputSuite<Input>()
        .addAll(Converter.convertBpp2d(BerkeyWangGenerator.getReferenceInstances()))
        .addAll(Converter.convertBpp2d(MartelloVigoGenerator.getReferenceInstances()));
    scenario.addInputSuite(berkeyWangSuite);

    // Set output verifier for scenario
    scenario.setVerifier(new OutputVerifier());

    // Run scenario
    CaparfCore<Input, Output> invoker = new CaparfCore<Input, Output>();
    invoker.addListener(new StatsCollectorListener<Input, Output>(new ContinuousBound()));
    invoker.run(scenario);
  }
}
