package ru.tvgu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.StorageService;
import ru.tvgu.service.PerceptronService;

import java.util.ArrayList;
import java.util.List;


/**
 * Сигмоида >= 0 => жив
 * Сигмоида < 0 => мертв
 */
@Service
@RequiredArgsConstructor
public class PerceptronServiceImpl implements PerceptronService {

    private static final Double TEACHING_SPEED = 0.3;


    private final List<List<Double>> hiddenLayerWeights = generateHiddenLayerWeights();

    private List<List<Double>> generateHiddenLayerWeights() {
        ArrayList<List<Double>> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                row.add(0.5);
            }
            result.add(row);
        }
        return result;
    }

    private final List<Double> outerLayerWeights = generateOuterLayerWeights();

    private List<Double> generateOuterLayerWeights() {
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            result.add(0.5);
        }
        return result;
    }

    private final StorageService storageService;


    @Override
    public void teach() {
        for (Passenger passenger : storageService.getCacheForTeaching()) {
            List<Double> hiddenLayerSumResults = hiddenLayerWeights.stream()
                    .map(neuronWeights -> getHiddenNeuronSumResult(passenger, neuronWeights))
                    .toList();
            List<Double> activatedHiddenLayerSumResults = hiddenLayerSumResults.stream()
                    .map(this::activateBySigmoid)
                    .toList();
            Double outerLayerResult = getOuterLayerResult(activatedHiddenLayerSumResults, outerLayerWeights);
            Double activatedOuterLayerResult = activateBySigmoid(outerLayerResult);
            Double outerLayerError = (passenger.getIsSurvived() - activatedOuterLayerResult)
                    * activateByDerivativeSigmoid(outerLayerResult);
            List<Double> outerLayerWeightsDifferences = activatedHiddenLayerSumResults.stream()
                    .map(weight -> getOuterLayerWeightDifference(weight, outerLayerError))
                    .toList();
            List<Double> hiddenLayerErrors = hiddenLayerSumResults.stream()
                    .map(sumResult -> getHiddenLayerError(outerLayerError, sumResult))
                    .toList();
            List<List<Double>> hiddenLayerWeightsDifferences = hiddenLayerErrors.stream()
                    .map(error -> getHiddenLayerWeightDifferences(passenger, error))
                    .toList();
            changeOuterLayerWeights(outerLayerWeightsDifferences);
            changeHiddenLayerWeights(hiddenLayerWeightsDifferences);

        }
        System.out.println(hiddenLayerWeights);
        System.out.println();
        System.out.println(outerLayerWeights);
    }

    @Override
    public void test(Integer count) {
        for (Passenger passenger: storageService.getCacheForTest(count)) {
            List<Double> hiddenLayerSumResults = hiddenLayerWeights.stream()
                    .map(neuronWeights -> getHiddenNeuronSumResult(passenger, neuronWeights))
                    .toList();
            List<Double> activatedHiddenLayerSumResults = hiddenLayerSumResults.stream()
                    .map(this::activateBySigmoid)
                    .toList();
            Double outerLayerResult = getOuterLayerResult(activatedHiddenLayerSumResults, outerLayerWeights);
            System.out.printf("Для %s пассажира%n", passenger);
            System.out.printf("До активации: %f%n", outerLayerResult);
            System.out.println(activateBySigmoid(outerLayerResult));
            System.out.println();
        }
    }

    private void changeHiddenLayerWeights(List<List<Double>> hiddenLayerWeightsDifferences) {
        for (int i = 0; i < hiddenLayerWeightsDifferences.size(); i++) {
            for (int j = 0; j < hiddenLayerWeightsDifferences.get(i).size(); j++) {
                hiddenLayerWeights.get(i).set(j,
                        hiddenLayerWeights.get(i).get(j) + hiddenLayerWeightsDifferences.get(i).get(j));
            }
        }
    }

    private void changeOuterLayerWeights(List<Double> outerLayerWeightsDifferences) {
        for (int i = 0; i < outerLayerWeightsDifferences.size(); i++) {
            outerLayerWeights.set(i, outerLayerWeights.get(i) + outerLayerWeightsDifferences.get(i));
        }
    }


    private List<Double> getHiddenLayerWeightDifferences(Passenger passenger, Double error) {
        return List.of(
                passenger.getCabinClass() * error,
                passenger.getIsAdult() * error,
                passenger.getIsMale() * error
        );
    }

    private Double getHiddenLayerError(Double outerError, Double layerSum) {
        return outerError * activateByDerivativeSigmoid(layerSum);
    }

    private Double getOuterLayerWeightDifference(Double weight, Double error) {
        return TEACHING_SPEED * weight * error;
    }

    private Double getOuterLayerResult(List<Double> activatedHiddenLayerSumResults, List<Double> outerLayerWeights) {
        Double sum = 0.;
        for (int i = 0; i< activatedHiddenLayerSumResults.size(); i++) {
            sum+=activatedHiddenLayerSumResults.get(i) * outerLayerWeights.get(i);
        }
        return sum;
    }

    private Double getHiddenNeuronSumResult(Passenger passenger, List<Double> neuronWeights) {
        return passenger.getCabinClass() * neuronWeights.get(0)
                + passenger.getIsAdult() * neuronWeights.get(1)
                + passenger.getIsMale() * neuronWeights.get(2);
    }

//    private Double activateBySigmoid(Double x) {
//        return 1. / (1. + Math.exp(-x));
//    }

    private Double activateBySigmoid(Double x) {
        return x >=0 ? 1. : 0.;
    }

//    private Double activateByDerivativeSigmoid(Double x) {
//        return activateBySigmoid(x) * (1. - activateBySigmoid(x));
//    }

    private Double activateByDerivativeSigmoid(Double x) {
        return 1.;
    }
}
