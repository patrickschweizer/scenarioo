/* scenarioo-client
 * Copyright (C) 2014, scenarioo.org Development Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

angular.module('scenarioo.services').factory('StepSketch', function ($log) {

    return {
        saveStepSketch: function (changedStepSketch, successCallback, errorCallback) {
            changedStepSketch.$save(function (updatedStepSketch) {
                if (successCallback) {
                    successCallback(updatedStepSketch);
                }
            },
            function (error) {
                $log.error(error);
                if (errorCallback) {
                    errorCallback('StepSketch could not be saved');
                }
            });
        }
    };

});
