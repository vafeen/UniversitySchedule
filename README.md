# University schedule

![Icon](icons/icon.png)

An application that will allow you to always know the actual schedule by link on Google Sheets.

# Application

Supporting languages: English, Russian

Supporting themes: Dark, Light

Main screen:

- Actual lessons for the current week and current lesson today
- Lessons with other frequencies
- If you’ve chosen a subgroup in Settings Screen, this screen shows you only lessons for the chosen subgroup; otherwise, for all subgroups

<p>
<img src="readmemedia/nextlesson.png" alt="Icon" width="210">
<img src="readmemedia/currentlesson.png" alt="Icon" width="210">
</p>
Settings screen:

_General_

- Set link to table with schedule
- View table in browser/GSheets app
- Change interface color by ColorPicker
- Choose subgroup (If not selected - will be selected, if selected - will be canceled)

_Contacts_

- Community of application in VK
- Code on GitHub

<img src="readmemedia/settingsscreen.png" alt="Icon" width="210">

_Color picker_

- You can change the main color separately in Dark and Light themes.

<img src="readmemedia/colorpicker.png" alt="Icon" width="210">

_Subgroup_

<p>
    <img src="readmemedia/subgroup.png" alt="Icon" width="210">
    <img src="readmemedia/chosensubgroup.png" alt="Icon" width="210">
</p>

# Table in Google Sheets

Required fields: A (day of week), C (start time), D (end time). But how without B (name)?

Lessons with undefined these columns will be skipped!!

Lessons with undefined following columns won’t be skipped:

when {

- column E (classroom) not defined -> won’t be shown for this lesson
- column F (name of teacher) not defined -> won’t be shown for this lesson
- column G (subgroup) not defined -> lesson for all subgroups
- column H (frequency) not defined -> lesson every week

}

![table](readmemedia/table.png)

To get data in mobile app:

1. Open public access
2. Copy the link to the table
3. Paste in the app: Settings screen -> Link to table

# Download

[releases](https://github.com/vafeen/UniversitySchedule/releases)
