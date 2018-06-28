# Smart-Parking-Application
A smart parking android application using raspberry pi and ultrasonic sensors

1. The ultrasonic sensors are connected in a grid across the parking area. The sensors detects whether a parking slot is vacant or not.
2. The information from the sensors is sent to the firebase.
3. The android application collects this information from the database and displays the vacancies in the parking area. The application        supports pre-booking service to book a slot in rush hours. The pre-booked slots are valid only for a half hour period.
4. The application scans a qr code if a user arrives at a parking slot and searches for a vacant slot to park.
