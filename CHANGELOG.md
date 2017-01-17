# Changelog

## Version 1.4 - January 17th 2017
- Upgrade Robolectric version to `3.2.1`
- Fix how `GroupViewHolder`'s expand and collapse method are called
- Avoid unnecessary conversion between `ExpandableListPosition` and group index
- Add layout for each sample activity
- Only update children check states if group is expanded
- Update how `clearChoices()` propagates view updates
- Remove unnecessary check for only `CheckedTextView` and toggle everything in `CheckableChildViewHolder`
- Update `ExpandActivity` sample to turn off flash animation

## Version 1.3 - December 15th 2016
- Add ability to programmatically toggle group using group
- Only expand / collapse groups if they have items
- Properly deserialize expandable group from Parcel

## Version 1.2 - November 14th 2016
- Ensure onSaveInstanceState is saving a `java.util.ArrayList`
- Replace `SparseBooleanArray` with Boolean Array in `CheckedExpandableGroup`
- Replace SparseBooleanArray in favor of Boolean array in `ExpandCollapseController`


## Version 1.1 - October 13th 2016
- Upgrade Gradle build tools to version `2.2.0`
- Fix bug where expand and collapse were reversed in `GroupViewHolder`

## Version 1.0 - August 16th 2016
Initial Release
